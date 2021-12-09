package com.pig4cloud.pig.dc.biz.controller;

import cn.felord.payment.wechat.v3.WechatApiProvider;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.dc.api.dto.OrderWriteOffDTO;
import com.pig4cloud.pig.dc.api.dto.QueryMemberOrderPageDTO;
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
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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
	@ApiIgnore
	@ApiOperation(value = " 查询订单数量统计", notes = " 查询订单数量统计" ,response = OrderCountVo.class)
	@PostMapping("/stastics")
	public R stastics(@RequestBody @Valid QueryOrderPageDTO dto) {
		return R.ok(oscOrderService.queryOrderStastics(dto));
	}


	/**
	 *  查询商品订单分页列表
	 *
	 * @return
	 */
	@ApiOperation(value = "查询商品订单分页列表", notes = " 查询商品订单分页列表",response = OrderVo.class)
	@PostMapping("/page")
	public R page(@RequestBody @Valid QueryOrderPageDTO dto) {
		return R.ok(oscOrderService.queryOrderPage(dto));
	}



	/**
	 * 根据id查询详情
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "根据id查询详情", notes = "根据id查询详情",response = OrderVo.class)
	@GetMapping("/details/{id}" )
	public R details(@PathVariable Integer id) {
		return R.ok(oscOrderService.details(id));
	}



	/**
	 *  订单核销
	 *
	 * @return
	 */
	@ApiOperation(value = "订单核销", notes = " 订单核销" )
	@PostMapping("/write_off")
	public R writeOff(@RequestBody @Valid OrderWriteOffDTO dto) {
		return R.ok(oscOrderService.writeOff(dto));
	}


	/**
	 *  查询会员订单分页列表
	 *
	 * @return
	 */
	@ApiOperation(value = "查询会员订单分页列表", notes = " 查询会员订单分页列表",response = OrderVo.class)
	@PostMapping("/member_order/page")
	public R queryMemberOrderPage(@RequestBody @Valid QueryMemberOrderPageDTO dto) {
		return R.ok(oscOrderService.queryMemberOrderPage(dto));
	}


	/**
	 * 根据id查询会员订单详情
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "根据id查询会员订单详情", notes = "根据id查询会员订单详情",response = OrderVo.class)
	@GetMapping("/member_order/details/{id}" )
	public R queryMemberOrderdDetails(@PathVariable Integer id) {
		return R.ok(oscOrderService.queryMemberOrderdDetails(id));
	}

}
