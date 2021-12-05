package com.pig4cloud.pig.dc.biz.controller;
import cn.felord.payment.wechat.v3.model.Payer;
import cn.felord.payment.wechat.v3.model.Amount;
import cn.felord.payment.wechat.v3.model.*;
import cn.felord.payment.wechat.v3.WechatResponseEntity;

import cn.felord.payment.wechat.v3.WechatApiProvider;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.dc.api.dto.WechatMiniPayDTO;
import com.pig4cloud.pig.dc.biz.config.Constant;
import com.pig4cloud.pig.dc.biz.config.WechatConfig;
import com.pig4cloud.pig.dc.biz.service.IOscAdministrativeDivisionService;
import com.pig4cloud.pig.dc.biz.service.IOscAreaService;
import com.pig4cloud.pig.dc.biz.service.IOscOfferService;
import com.pig4cloud.pig.dc.biz.service.IOscOrderService;
import com.pig4cloud.pig.dc.biz.utils.RandomGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
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

	/**
	 *  小程序下单(预支付)
	 *
	 * @return
	 */
	@ApiOperation(value = "小程序下单(预支付)", notes = "小程序下单(预支付)")
	@PostMapping("/prepay")
	public R createOrder(@RequestBody @Valid WechatMiniPayDTO dto) {

		/*PayParams params=new PayParams();
		params.setAppid(wechatConfig.getAppId());
		params.setMchid(wechatConfig.getMchId());
		params.setDescription("测试");
		params.setOutTradeNo(RandomGenerator.generateRandomByDate(100000));
		//params.setTimeExpire("");
		//params.setAttach("test");
		params.setNotifyUrl(wechatConfig.getNotifyUrl());
		//params.setGoodsTag("");
		Amount amount=new Amount();
		amount.setTotal(1);
		amount.setCurrency("CNY");
		params.setAmount(amount);

		Payer payer=new Payer();
		payer.setOpenid(dto.getOpenId());
		params.setPayer(payer);

		WechatResponseEntity<ObjectNode> mini = wechatApiProvider.directPayApi(Constant.TANANTID).jsPay(params);*/
		return R.ok(oscOrderService.prepay(dto));
	}


	/**
	 * 微信支付成功回调.
	 * <p>
	 * 无需开发者判断，只有扣款成功微信才会回调此接口
	 *

	 */
	@ApiIgnore
	@ApiOperation(value = "订单支付成功的回调", notes = "订单支付成功的回调")
	@SneakyThrows
	@PostMapping("/wechat/notify")
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
	@PostMapping("/transaction")
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

		});
	}





}
