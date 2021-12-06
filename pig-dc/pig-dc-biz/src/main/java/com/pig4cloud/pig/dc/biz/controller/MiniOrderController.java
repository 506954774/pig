package com.pig4cloud.pig.dc.biz.controller;
import cn.felord.payment.wechat.enumeration.TradeState;
import cn.felord.payment.wechat.v3.model.Payer;
import cn.felord.payment.wechat.v3.model.Amount;
import cn.felord.payment.wechat.v3.model.*;
import cn.felord.payment.wechat.v3.WechatResponseEntity;

import cn.felord.payment.wechat.v3.WechatApiProvider;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.dc.api.dto.*;
import com.pig4cloud.pig.dc.api.entity.OscOrder;
import com.pig4cloud.pig.dc.api.entity.OscUserInfo;
import com.pig4cloud.pig.dc.api.vo.OrderCountVo;
import com.pig4cloud.pig.dc.api.vo.OrderVo;
import com.pig4cloud.pig.dc.biz.config.Constant;
import com.pig4cloud.pig.dc.biz.config.WechatConfig;
import com.pig4cloud.pig.dc.biz.enums.OrderStatusEnum;
import com.pig4cloud.pig.dc.biz.enums.OrderTypeEnum;
import com.pig4cloud.pig.dc.biz.service.*;
import com.pig4cloud.pig.dc.biz.utils.RandomGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author LeiChen
 * @version 1.0
 * @date 2021/5/21 12:37
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/mini/order")
@Api(value = "MiniOrderController", tags = "小程序-订单接口")
public class MiniOrderController {

	private final IOscAreaService iOscAreaService;
	private final IOscAdministrativeDivisionService iOscAdministrativeDivisionService;



	private final WechatApiProvider wechatApiProvider;
	private final WechatConfig wechatConfig;
	private final IOscOrderService oscOrderService;
	private final IOscUserInfoService oscUserInfoService;

	/**
	 *  小程序下单(预支付)
	 *
	 * @return
	 */
	@ApiOperation(value = "小程序下单(预支付)", notes = "小程序下单(预支付)")
	@PostMapping("/prepay")
	public R createOrder(@RequestBody @Valid WechatMiniPayDTO dto) {
		return R.ok(oscOrderService.prepay(dto));
	}

	/**
	 *  根据预支付id,查询支付结果
	 *
	 * @return
	 */
	@ApiOperation(value = "根据预支付id,查询支付结果", notes = "根据预支付id,查询支付结果")
	@GetMapping("/query_pay_result/{prepay_id}")
	public R queryPayResult(@PathVariable String prepay_id) {
		return R.ok(oscOrderService.queryPayResult(prepay_id));
	}


	/**
	 * 测试微信支付成功回调.
	 * <p>
	 * 无需开发者判断，只有扣款成功微信才会回调此接口
	 *

	 */
	@ApiIgnore
	@ApiOperation(value = "测试微信支付成功回调", notes = "测试微信支付成功回调测试微信支付成功回调")
	@SneakyThrows
	@PostMapping("/transaction")
	public Map<String, ?> notify(BufferedReader br,
								 HttpServletRequest request) {
		log.info("=================================================================微信回调");
		Enumeration<?> enum1 = request.getHeaderNames();
		while (enum1.hasMoreElements()) {
			String key = (String) enum1.nextElement();
			String value = request.getHeader(key);
			log.info(key + "\t" + value);
		}
//body部分
		String inputLine;
		String str = "";
		try {
			while ((inputLine = br.readLine()) != null) {
				str += inputLine;
			}
			br.close();
		} catch (IOException e) {
			log.error("IOException: " + e);
		}
		log.info("str:" + str);
		 return null;
	}


	/**
	 * 微信支付成功回调.
	 * 这个地址是配置在yml里的.由域名和notifyUrl拼接而成
	 * notifyUrl只写域名后面的就行
	 *  domain: https://rdch.sfwal.com
	 *  notifyUrl: /dc/mini/order/wechat/notify
	 * <p>
	 * 无需开发者判断，只有扣款成功微信才会回调此接口
	 *
	 * @param wechatpaySerial    the wechatpay serial
	 * @param wechatpaySignature the wechatpay signature
	 * @param wechatpayTimestamp the wechatpay timestamp
	 * @param wechatpayNonce     the wechatpay nonce
	 * @param request            the request
	 * @return the map
	 */
	@ApiIgnore
	@ApiOperation(value = "订单支付成功的回调", notes = "订单支付成功的回调")
	@SneakyThrows
	@Transactional(rollbackFor = Exception.class)
	@PostMapping("/wechat/notify")

	public Map<String, ?> transactionCallback(
			@RequestHeader("Wechatpay-Serial") String wechatpaySerial,
			@RequestHeader("Wechatpay-Signature") String wechatpaySignature,
			@RequestHeader("Wechatpay-Timestamp") String wechatpayTimestamp,
			@RequestHeader("Wechatpay-Nonce") String wechatpayNonce,
			HttpServletRequest request) {
		log.info("=================================================================微信回调transactionCallback");

		String body = request.getReader().lines().collect(Collectors.joining());
		// 对请求头进行验签 以确保是微信服务器的调用
		ResponseSignVerifyParams params = new ResponseSignVerifyParams();
		params.setWechatpaySerial(wechatpaySerial);
		params.setWechatpaySignature(wechatpaySignature);
		params.setWechatpayTimestamp(wechatpayTimestamp);
		params.setWechatpayNonce(wechatpayNonce);
		params.setBody(body);
		return wechatApiProvider.callback(Constant.TANANTID).transactionCallback(params, data -> {
			//TODO 对回调解析的结果进行消费
			log.info("对回调解析的结果进行消费,{}",data);

			if(data.getTradeState().equals(TradeState.SUCCESS)){
				log.info("支付成功,data.getTradeState():{}",data.getTradeState());

				Date now =new Date();
				//获取订单编号
				String outTradeNo = data.getOutTradeNo();

				List<OscOrder> oscOrders = oscOrderService.getBaseMapper().selectList(Wrappers.<OscOrder>query().lambda().eq(OscOrder::getOrderNum, outTradeNo));

				if(CollectionUtils.isNotEmpty(oscOrders)){
					OscOrder oscOrder=oscOrders.get(0);
					//买会员
					if(oscOrder.getOrderType().equals(OrderTypeEnum.MEMBER.getTypeCode())){
						//会员状态修改
						OscUserInfo oscUserInfo = oscUserInfoService.getBaseMapper().selectById(oscOrder.getUserId());
						if(oscUserInfo!=null){
							oscUserInfo.setMemberFlag(Constant.MEMBER_FLAG);
							oscUserInfo.setMemberPayTime(now);
							oscUserInfoService.getBaseMapper().updateById(oscUserInfo);
						}
					}
					//买产品
					else{

					}
					oscOrder.setOrderStatus(OrderStatusEnum.PAID.getTypeCode());
					oscOrder.setPayTime(now);
					oscOrder.setThirdPartyId(data.getTransactionId());
					oscOrderService.getBaseMapper().updateById(oscOrder);
					log.info("更新订单支付状态成功:{}",oscOrder.getId());
				}

			}

		});
	}



	/**
	 *  根据uid查询订单数量统计
	 *
	 * @return
	 */
	@ApiOperation(value = " 根据uid查询订单数量统计", notes = " 根据uid查询订单数量统计" ,response = OrderCountVo.class)
	@PostMapping("/stastics")
	public R stastics(@RequestBody @Valid QueryUserOrderStasticsDTO dto) {
		return R.ok(oscOrderService.queryOrderStastics(BeanUtil.copyProperties(dto,QueryOrderPageDTO.class)));
	}


	/**
	 *  根据uid查询订单分页列表
	 *
	 * @return
	 */
	@ApiOperation(value = " 根据uid查询订单分页列表", notes = " 根据uid查询订单分页列表",response = OrderVo.class)
	@PostMapping("/page")
	public R page(@RequestBody @Valid QueryUserOrderPageDTO dto) {
		return R.ok(oscOrderService.queryOrderPage(BeanUtil.copyProperties(dto,QueryOrderPageDTO.class)));
	}


}
