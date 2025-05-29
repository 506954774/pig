package com.pig4cloud.pig.dc.biz.controller;

import cn.hutool.core.bean.BeanUtil;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.dc.api.entity.OscSysParamMemberHint;
import com.pig4cloud.pig.dc.api.entity.OscSysParamMemberPrice;
import com.pig4cloud.pig.dc.biz.config.Constant;
import com.pig4cloud.pig.dc.biz.service.IOscAdministrativeDivisionService;
import com.pig4cloud.pig.dc.biz.service.IOscSysParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author LeiChen
 * @version 1.0
 * @date 2021/5/21 12:37
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sys")
@Api(value = "MiniAreaController", tags = "系统参数模块")
public class SystemController {

	private final IOscSysParamService oscSysParamService;
	private final IOscAdministrativeDivisionService iOscAdministrativeDivisionService;


	/**
	 * 查询全部系统参数
	 * @return R
	 */
	@ApiOperation(value = "查询全部系统参数", notes = "查询全部系统参数")
	@GetMapping("/list" )
	public R list() {
		return R.ok(oscSysParamService.getBaseMapper().selectList(null));
	}


	/**
	 *  设置会员价格
	 *
	 * @return
	 */
	@ApiOperation(value = " 设置会员价格", notes = " 设置会员价格" )
	@PostMapping("/set_member_price")
	public R resetMemberPrice(@RequestBody @Valid OscSysParamMemberPrice dto) {
		return R.ok(oscSysParamService.resetParam(Constant.MEMBER_PRICE,dto));
	}


	/**
	 *  设置会员优惠的描述json串
	 *
	 * @return
	 */
	@ApiOperation(value = " 设置会员优惠的描述json串", notes = " 设置会员优惠的描述json串" )
	@PostMapping("/set_member_hint")
	public R resetMemberHint(@RequestBody @Valid OscSysParamMemberHint dto) {
		return R.ok(oscSysParamService.resetParam(Constant.MEMBER_HINT, BeanUtil.copyProperties(dto,OscSysParamMemberPrice.class)));
	}






}
