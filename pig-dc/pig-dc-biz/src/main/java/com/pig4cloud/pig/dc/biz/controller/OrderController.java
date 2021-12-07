package com.pig4cloud.pig.dc.biz.controller;

import cn.felord.payment.wechat.v3.WechatApiProvider;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.dc.api.dto.QueryOrderPageDTO;
import com.pig4cloud.pig.dc.api.vo.OrderCountVo;
import com.pig4cloud.pig.dc.api.vo.OrderVo;
import com.pig4cloud.pig.dc.biz.config.WechatConfig;
import com.pig4cloud.pig.dc.biz.service.IOscAdministrativeDivisionService;
import com.pig4cloud.pig.dc.biz.service.IOscAreaService;
import com.pig4cloud.pig.dc.biz.service.IOscOrderService;
import com.pig4cloud.pig.dc.biz.service.IOscUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author LeiChen
 * @version 1.0
 * @date 2021/5/21 12:37
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/order")
@Api(value = "OrderController", tags = "订单管理")
public class OrderController {

	private final IOscAreaService iOscAreaService;
	private final IOscAdministrativeDivisionService iOscAdministrativeDivisionService;



	private final WechatApiProvider wechatApiProvider;
	private final WechatConfig wechatConfig;
	private final IOscOrderService oscOrderService;
	private final IOscUserInfoService oscUserInfoService;



	/**
	 *  查询订单数量统计
	 *
	 * @return
	 */
	@ApiOperation(value = " 查询订单数量统计", notes = " 查询订单数量统计" ,response = OrderCountVo.class)
	@PostMapping("/stastics")
	public R stastics(@RequestBody @Valid QueryOrderPageDTO dto) {
		return R.ok(oscOrderService.queryOrderStastics(dto));
	}


	/**
	 *  根据uid查询订单分页列表
	 *
	 * @return
	 */
	@ApiOperation(value = "查询订单分页列表", notes = " 查询订单分页列表",response = OrderVo.class)
	@PostMapping("/page")
	public R page(@RequestBody @Valid QueryOrderPageDTO dto) {
		return R.ok(oscOrderService.queryOrderPage(dto));
	}


}
