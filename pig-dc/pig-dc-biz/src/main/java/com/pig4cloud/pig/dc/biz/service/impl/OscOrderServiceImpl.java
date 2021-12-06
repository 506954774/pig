package com.pig4cloud.pig.dc.biz.service.impl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import cn.felord.payment.wechat.v3.WechatApiProvider;
import cn.felord.payment.wechat.v3.WechatResponseEntity;
import cn.felord.payment.wechat.v3.model.Amount;
import cn.felord.payment.wechat.v3.model.PayParams;
import cn.felord.payment.wechat.v3.model.Payer;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pig4cloud.pig.dc.api.dto.PrepayOrderDTO;
import com.pig4cloud.pig.dc.api.dto.WechatMiniPayDTO;
import com.pig4cloud.pig.dc.api.dto.WechatMiniPayGoodsDTO;
import com.pig4cloud.pig.dc.api.entity.*;
import com.pig4cloud.pig.dc.biz.config.Constant;
import com.pig4cloud.pig.dc.biz.config.WechatConfig;
import com.pig4cloud.pig.dc.biz.enums.OrderStatusEnum;
import com.pig4cloud.pig.dc.biz.enums.OrderTypeEnum;
import com.pig4cloud.pig.dc.biz.exceptions.BizException;
import com.pig4cloud.pig.dc.biz.mapper.*;
import com.pig4cloud.pig.dc.biz.rabbitMq.receiver.MessageType;
import com.pig4cloud.pig.dc.biz.service.IOscOrderService;
import com.pig4cloud.pig.dc.biz.utils.RandomGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pig4cloud.pig.common.mq.constants.RabbitMqConstants;
import pig4cloud.pig.common.mq.sender.RabbitMqSender;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author chenlei
 * @since 2021-12-05
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OscOrderServiceImpl extends ServiceImpl<OscOrderMapper, OscOrder> implements IOscOrderService {



	private final WechatApiProvider wechatApiProvider;
	private final WechatConfig wechatConfig;
	private final OscUserInfoMapper oscUserInfoMapper;
	private final OscSysParamMapper oscSysParamMapper;
	private final OscProductMapper oscProductMapper;
	private final OscOrderProductMapper oscOrderProductMapper;
	private final RabbitMqSender rabbitMqSender;


	@Transactional(rollbackFor = Exception.class)
	@Override
	public WechatResponseEntity<ObjectNode> prepay(WechatMiniPayDTO payDTO) {
		//根据openId查出用户id
		List<OscUserInfo> oscUserInfos = oscUserInfoMapper.selectList(Wrappers.<OscUserInfo>query().lambda()
				.eq(OscUserInfo::getOpenid, payDTO.getOpenId())
				.orderByDesc(OscUserInfo::getCreateTime)
		);
		if(CollectionUtils.isEmpty(oscUserInfos)){
			throw new BizException("用户不存在或者已被删除");
		}
		else {
			PrepayOrderDTO prepayOrderDTO=null;
			OscOrder order=null;
			//购买会员
			if(payDTO.getType().equals(OrderTypeEnum.MEMBER.getTypeCode())){
				order=generateMemberOrder(oscUserInfos.get(0));
			}
			//购买商品
			else {
				if(CollectionUtils.isEmpty(payDTO.getGoods())){
					throw new BizException("产品列表不可为空");
				}
				prepayOrderDTO=generateProductsOrder(oscUserInfos.get(0),payDTO.getGoods());
				order=prepayOrderDTO.getOrder();
			}

			//调用微信接口
			WechatResponseEntity<ObjectNode> objectNodeWechatResponseEntity = wechatPrepay(order);
			order.setPrepayId(objectNodeWechatResponseEntity.getBody().get(Constant.PREPAY_ID).asText());

			//订单入库
			getBaseMapper().insert(order);
			//订单详情入库
			if(payDTO.getType().equals(OrderTypeEnum.PRODUCT.getTypeCode())){
				ArrayList<OscOrderProduct> orderProducts  =prepayOrderDTO.getOrderProducts();
				for (int i = 0; i < orderProducts.size(); i++) {
					orderProducts.get(i).setOrderId(order.getId());
					oscOrderProductMapper.insert(orderProducts.get(i));
				}
			}


			MessageType message=new MessageType();
			message.setType(MessageType.TYPE_OREDER_CANCEL);
			message.setId(order.getId());


			//发送延时消息
			rabbitMqSender.sendDelayMessage(RabbitMqConstants.EXCHANGE_TEST,
					RabbitMqConstants.TOPIC_TEST, JSON.toJSONString(message), Constant.DELAY_TIME);

			return objectNodeWechatResponseEntity;
			//return null;
		}
	}

	@Override
	public WechatResponseEntity<ObjectNode> wechatPrepay(OscOrder order) {
		PayParams params=new PayParams();
		params.setAppid(wechatConfig.getAppId());
		params.setMchid(wechatConfig.getMchId());
		params.setDescription(order.getOrderDesc());
		params.setOutTradeNo(order.getOrderNum());
		//params.setTimeExpire("");
		//params.setAttach("test");
		params.setNotifyUrl(wechatConfig.getNotifyUrl());
		log.info("jspay,支付回调地址:"+wechatConfig.getNotifyUrl());
		//params.setGoodsTag("");
		Amount amount=new Amount();
		BigDecimal price= Constant.TIMES.multiply(order.getOrderAmount());
		amount.setTotal(price.intValue());
		amount.setCurrency(Constant.CNY);
		params.setAmount(amount);

		Payer payer=new Payer();
		payer.setOpenid(order.getOpenId());
		params.setPayer(payer);

		WechatResponseEntity<ObjectNode> mini = wechatApiProvider.directPayApi(Constant.TANANTID).jsPay(params);

		return mini;
	}

	@Override
	public Integer queryPayResult(String prepayId) {
		List<OscOrder> oscOrders =  getBaseMapper().selectList(Wrappers.<OscOrder>query().lambda().eq(OscOrder::getPrepayId, prepayId));
		if(CollectionUtils.isNotEmpty(oscOrders)){
			OscOrder oscOrder=oscOrders.get(0);
			if(oscOrder.getOrderStatus().equals(OrderStatusEnum.PAID.getTypeCode())){
				return OrderStatusEnum.PAID.getTypeCode();
			}
		}
		return OrderStatusEnum.PREPAY.getTypeCode();
	}

	/***
	 * 生成会员订单
	 * @param user
	 * @return
	 */
	private OscOrder generateMemberOrder(OscUserInfo user){

		//查出会员价格
		String price="15.00";
		List<OscSysParam> oscSysParams = oscSysParamMapper.selectList(Wrappers.<OscSysParam>query().lambda().eq(OscSysParam::getParamKey, Constant.MEMBER_PRICE).orderByDesc(OscSysParam::getCreateTime));
		if(CollectionUtils.isNotEmpty(oscSysParams)){
			price=oscSysParams.get(0).getParamValue();
		}

		OscOrder order=new OscOrder();
		order.setOrderNum(RandomGenerator.generateRandomByDate(Constant.ORDER_LIMIT));
		order.setOrderType(OrderTypeEnum.MEMBER.getTypeCode());
		//BigDecimal amount= Constant.TIMES.multiply(new BigDecimal(price));
		order.setOrderAmount(new BigDecimal(price));
		order.setOrderStatus(OrderStatusEnum.PREPAY.getTypeCode());
		order.setUserId(user.getUserId());
		order.setOpenId(user.getOpenid());
		order.setOrderDesc(OrderTypeEnum.MEMBER.getTypeName());
		log.info("=============generateMemberOrder,{}",order);

		return order;
	}

	/**
	 * @Name:
	 * @Description: 生成产品订单
	 * @Param:
	 * @return:
	 * @Author: LeiChen
	 * @Date:2021/12/5 17:29
	 *
	 * */
	private PrepayOrderDTO generateProductsOrder(OscUserInfo user, List<WechatMiniPayGoodsDTO> goods){
		PrepayOrderDTO result=new PrepayOrderDTO();
		BigDecimal amount=new BigDecimal(0);
		ArrayList<OscOrderProduct> list=new ArrayList<>();

		List<OscProduct> oscProducts = oscProductMapper.selectList(Wrappers.<OscProduct>query().lambda()
				.in(OscProduct::getId, goods.stream().map(bean -> bean.getGoodsId()).collect(Collectors.toList()))
		);
		if(CollectionUtils.isEmpty(oscProducts)){
			throw new BizException("商品不存在或者已被删除");
		}
		else {
			var productsMaps=oscProducts.stream().collect(Collectors.toMap(bean->bean.getId(), bean->bean));
			for(WechatMiniPayGoodsDTO dto:goods){
				OscProduct oscProduct = productsMaps.get(dto.getGoodsId());
				if(oscProduct==null){
					throw new BizException("商品不存在或者已被删除,id:"+dto.getGoodsId());
				}
				if(dto.getQuantity()==null||dto.getQuantity().compareTo(0)<=0){
					throw new BizException("商品数量不可小于0,id:"+dto.getGoodsId());
				}
				else{
					Date now=new Date();
					BigDecimal prices=new BigDecimal(oscProduct.getProductPrice()).multiply(new BigDecimal(dto.getQuantity()));
					amount=amount.add(prices);

					OscOrderProduct product=new OscOrderProduct();
					product.setProductId(oscProduct.getId());
					product.setProductQuantity(dto.getQuantity());
					product.setProductTotalPrice(prices);
					product.setProductName(oscProduct.getProductName());
					product.setProductSinglePrice(new BigDecimal(oscProduct.getProductPrice()));
					product.setCreateTime(now);
					list.add(product);
				}
			}
		}

		OscOrder order=new OscOrder();
		order.setOrderNum(RandomGenerator.generateRandomByDate(Constant.ORDER_LIMIT));
		order.setOrderType(OrderTypeEnum.PRODUCT.getTypeCode());
		//BigDecimal amount= Constant.TIMES.multiply(new BigDecimal(price));
		order.setOrderAmount(amount);
		order.setOrderStatus(OrderStatusEnum.PREPAY.getTypeCode());
		order.setUserId(user.getUserId());
		order.setOpenId(user.getOpenid());
		order.setOrderDesc(getGoodsDesc(oscProducts));
		log.info("=============generateProductsOrder,{}",order);

		result.setOrder(order);
		result.setOrderProducts(list);
		return result;
	}

	private String getGoodsDesc(List<OscProduct> oscProducts){
		StringBuilder stringBuilder=new StringBuilder();
		for(OscProduct dto:oscProducts){
			stringBuilder.append(dto.getProductName());
			stringBuilder.append(Constant.SEPCTOR);
		}
		stringBuilder.deleteCharAt(stringBuilder.length()-1);
		return stringBuilder.toString();
	}
}
