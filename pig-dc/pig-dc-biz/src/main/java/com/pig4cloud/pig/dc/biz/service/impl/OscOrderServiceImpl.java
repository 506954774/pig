package com.pig4cloud.pig.dc.biz.service.impl;
import com.google.common.collect.Lists;
import cn.felord.payment.wechat.v3.model.RefundParams.RefundAmount;

import cn.felord.payment.wechat.v3.WechatApiProvider;
import cn.felord.payment.wechat.v3.WechatResponseEntity;
import cn.felord.payment.wechat.v3.model.Amount;
import cn.felord.payment.wechat.v3.model.PayParams;
import cn.felord.payment.wechat.v3.model.Payer;
import cn.felord.payment.wechat.v3.model.RefundParams;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.dc.api.dto.*;
import com.pig4cloud.pig.dc.api.entity.*;
import com.pig4cloud.pig.dc.api.vo.BodyDTO;
import com.pig4cloud.pig.dc.api.vo.OrderCountVo;
import com.pig4cloud.pig.dc.api.vo.OrderVo;
import com.pig4cloud.pig.dc.api.vo.WechatPayResponse;
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
import org.apache.http.util.TextUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pig4cloud.pig.common.mq.constants.RabbitMqConstants;
import pig4cloud.pig.common.mq.sender.RabbitMqSender;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * ?????? ???????????????
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
	private final RedisTemplate<String, String> redisTemplate;



	@Transactional(rollbackFor = Exception.class)
	@Override
	public WechatResponseEntity<ObjectNode> prepay(WechatMiniPayDTO payDTO) {
		//??????openId????????????id
		List<OscUserInfo> oscUserInfos = oscUserInfoMapper.selectList(Wrappers.<OscUserInfo>query().lambda()
				.eq(OscUserInfo::getOpenid, payDTO.getOpenId())
				.orderByDesc(OscUserInfo::getCreateTime)
		);
		if(CollectionUtils.isEmpty(oscUserInfos)){
			throw new BizException("?????????????????????????????????");
		}
		else {
			PrepayOrderDTO prepayOrderDTO=null;
			OscOrder order=null;
			//????????????
			if(payDTO.getType().equals(OrderTypeEnum.MEMBER.getTypeCode())){
				order=generateMemberOrder(oscUserInfos.get(0));
			}
			//????????????
			else {
				if(CollectionUtils.isEmpty(payDTO.getGoods())){
					throw new BizException("????????????????????????");
				}
				prepayOrderDTO=generateProductsOrder(oscUserInfos.get(0),payDTO.getGoods());
				order=prepayOrderDTO.getOrder();
			}

			//??????????????????
			WechatResponseEntity<ObjectNode> objectNodeWechatResponseEntity = wechatPrepay(order);
			order.setPrepayId(objectNodeWechatResponseEntity.getBody().get(Constant.PREPAY_ID).asText());

			//????????????
			order.setUserRemark(payDTO.getUserRemark());
			getBaseMapper().insert(order);
			//??????????????????
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


			//??????????????????
			rabbitMqSender.sendDelayMessage(RabbitMqConstants.EXCHANGE_TEST,
					RabbitMqConstants.TOPIC_TEST, JSON.toJSONString(message), Constant.DELAY_TIME);


			//??????redis.????????????????????????,?????????????????????pojo,???????????????????????????????????????
			BodyDTO bodyDTO=new BodyDTO();
			bodyDTO.setPrepayId(objectNodeWechatResponseEntity.getBody().get(Constant.PREPAY_ID).asText());
			bodyDTO.setAppId(objectNodeWechatResponseEntity.getBody().get("appId").asText());
			bodyDTO.setTimeStamp(objectNodeWechatResponseEntity.getBody().get("timeStamp").asText());
			bodyDTO.setNonceStr(objectNodeWechatResponseEntity.getBody().get("nonceStr").asText());
			bodyDTO.setPackageX(objectNodeWechatResponseEntity.getBody().get("package").asText());
			bodyDTO.setSignType(objectNodeWechatResponseEntity.getBody().get("signType").asText());
			bodyDTO.setPaySign(objectNodeWechatResponseEntity.getBody().get("paySign").asText());

			redisTemplate.opsForValue().set(order.getId().toString(),JSON.toJSONString(bodyDTO),Constant.DELAY_TIME, TimeUnit.MILLISECONDS);

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
		log.info("jspay,??????????????????:"+wechatConfig.getNotifyUrl());
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

	@Override
	public List<OrderCountVo> queryOrderStastics(QueryOrderPageDTO dto) {
		List<OrderCountVo> result=new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			OrderCountVo bean=new OrderCountVo();
			bean.setOrderStatus(i+1);
			bean.setOrderCount(0);
			result.add(bean);
		}
		List<OrderCountVo> orderCountVos = getBaseMapper().selectOrdersCount(dto);
		//log.info("????????????,{}",orderCountVos);
		if(CollectionUtils.isNotEmpty(orderCountVos)){
			var maps=orderCountVos.stream().collect(Collectors.toMap(bean->bean.getOrderStatus().toString(),bean->bean.getOrderCount()));
			//log.info("????????????,maps{}",maps);
			for (int i = 0; i < result.size(); i++) {
				Integer count= maps.get(result.get(i).getOrderStatus().toString());
				if(count!=null){
					result.get(i).setOrderCount(count);
				}
			}
		}
		return result;
	}

	@Override
	public Page<OrderVo> queryOrderPage(QueryOrderPageDTO dto) {
		if(dto==null){
			dto=new QueryOrderPageDTO();
		}
		Page page=new Page();
		page.setCurrent(dto.getCurrent());
		page.setSize(dto.getSize());
		Page page1 = getBaseMapper().selectOrders(page, dto);
		return page1;
	}

	@Override
	public Integer cancelOrder(UserOrderDTO dto) {
		OscOrder oscOrder = getBaseMapper().selectById(dto.getOrderId());
		if(oscOrder==null){
			throw new BizException("?????????????????????????????????");
		}
		if(!oscOrder.getOrderStatus().equals(OrderStatusEnum.PREPAY.getTypeCode())){
			throw new BizException("?????????????????????????????????,????????????");
		}
		if(!oscOrder.getUserId().equals(dto.getUserId())){
			throw new BizException("???????????????????????????,????????????");
		}
		oscOrder.setCancelTime(new Date());
		oscOrder.setOrderStatus(OrderStatusEnum.CANCEL.getTypeCode());
		getBaseMapper().updateById(oscOrder);
		return oscOrder.getId();
	}

	@Override
	public WechatPayResponse continue2Pay(UserOrderDTO dto) {
		OscOrder oscOrder = getBaseMapper().selectById(dto.getOrderId());
		if(oscOrder==null){
			throw new BizException("?????????????????????????????????");
		}
		if(!oscOrder.getOrderStatus().equals(OrderStatusEnum.PREPAY.getTypeCode())){
			throw new BizException("?????????????????????????????????,??????????????????");
		}
		if(!oscOrder.getUserId().equals(dto.getUserId())){
			throw new BizException("???????????????????????????,??????????????????");
		}
		//???redis???????????????
		String cache = redisTemplate.opsForValue().get(oscOrder.getId().toString());
		if(TextUtils.isEmpty(cache)){
			throw new BizException("?????????????????????,????????????,??????????????????");
		}
		else{
			WechatPayResponse responseEntity=new WechatPayResponse();
			responseEntity.setHttpStatus(200);
			responseEntity.setBody(JSON.parseObject(cache,BodyDTO.class));
			return responseEntity;
		}
		/*WechatResponseEntity cache = redisTemplate.opsForValue().get(oscOrder.getId().toString());
		log.info("?????????????????????,??????:[]",cache);
		if(cache==null){
			throw new BizException("?????????????????????,????????????,??????????????????");
		}
		else{
			return cache;
		}*/
	}

	@Override
	public OrderVo details(Integer id) {
		return getBaseMapper().selectOrderById(id);
	}

	@Override
	public Integer writeOff(OrderWriteOffDTO dto) {
		OscOrder oscOrder = getBaseMapper().selectById(dto.getOrderId());
		if(oscOrder==null){
			throw new BizException("?????????????????????????????????");
		}
		if(!oscOrder.getOrderStatus().equals(OrderStatusEnum.PAID.getTypeCode())){
			throw new BizException("?????????????????????????????????,????????????");
		}
		oscOrder.setWriteOffTime(new Date());
		oscOrder.setWriteOffBy(SecurityUtils.getUser().getId()+"");
		oscOrder.setOrderStatus(OrderStatusEnum.FINISHED.getTypeCode());
		getBaseMapper().updateById(oscOrder);
		return oscOrder.getId();
	}

	@Override
	public Page<OrderVo> queryMemberOrderPage(QueryMemberOrderPageDTO dto) {
		if(dto==null){
			dto=new QueryMemberOrderPageDTO();
		}
		Page page=new Page();
		page.setCurrent(dto.getCurrent());
		page.setSize(dto.getSize());
		Page page1 = getBaseMapper().selectMemberOrders(page, dto);
		return page1;
	}

	@Override
	public OrderVo queryMemberOrderdDetails(Integer id) {
		return getBaseMapper().selectMemberOrderById(id);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Integer refund(OrderWriteOffDTO dto) {
		OscOrder oscOrder = getBaseMapper().selectById(dto.getOrderId());
		if(oscOrder==null){
			throw new BizException("?????????????????????????????????");
		}
		if(!oscOrder.getOrderStatus().equals(OrderStatusEnum.PAID.getTypeCode())){
			throw new BizException("?????????????????????????????????,????????????");
		}

		oscOrder.setRefundNum(RandomGenerator.generateRandomByDate(Constant.ORDER_LIMIT));

		RefundParams params=new RefundParams();
		params.setTransactionId(oscOrder.getThirdPartyId());
		params.setOutTradeNo(oscOrder.getOrderNum());
		params.setOutRefundNo(oscOrder.getRefundNum());
		//params.setReason("");
		params.setNotifyUrl(wechatConfig.getRefundNotifyUrl());
		//params.setFundsAccount("");
		RefundAmount amount=new RefundAmount();
		//amount.setForm(Lists.newArrayList());
		BigDecimal price= Constant.TIMES.multiply(oscOrder.getOrderAmount());
		amount.setTotal(price.intValue());
		amount.setCurrency(Constant.CNY);
		amount.setRefund(price.intValue());
		params.setAmount(amount);
		//params.setGoodsDetail(Lists.newArrayList());

		log.info("????????????,{}",params);
		wechatApiProvider.directPayApi(Constant.TANANTID).refund(params);

		oscOrder.setRefundOperator(SecurityUtils.getUser().getId()+"");
		oscOrder.setOrderStatus(OrderStatusEnum.APPLIED.getTypeCode());
		getBaseMapper().updateById(oscOrder);

		return oscOrder.getId();
	}

	/***
	 * ??????????????????
	 * @param user
	 * @return
	 */
	private OscOrder generateMemberOrder(OscUserInfo user){

		//??????????????????
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
	 * @Description: ??????????????????
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
			throw new BizException("?????????????????????????????????");
		}
		else {
			var productsMaps=oscProducts.stream().collect(Collectors.toMap(bean->bean.getId(), bean->bean));
			for(WechatMiniPayGoodsDTO dto:goods){
				OscProduct oscProduct = productsMaps.get(dto.getGoodsId());
				if(oscProduct==null){
					throw new BizException("?????????????????????????????????,id:"+dto.getGoodsId());
				}
				if(dto.getQuantity()==null||dto.getQuantity().compareTo(0)<=0){
					throw new BizException("????????????????????????0,id:"+dto.getGoodsId());
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
