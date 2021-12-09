package com.pig4cloud.pig.dc.biz.controller;

import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.dc.api.dto.QueryExchangeRateDTO;
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
@RequestMapping("/mini/sys")
@Api(value = "MiniAreaController", tags = "小程序-系统参数查询接口")
public class MiniSystemController {

	private final IOscSysParamService oscSysParamService;
	private final IOscAdministrativeDivisionService iOscAdministrativeDivisionService;


	/**
	 * 查询全部系统参数
	 * @return R
	 */
	@ApiOperation(value = "查询全部系统参数", notes = "查询全部系统参数")
	@GetMapping("/list" )
	public R list( ) {
		return R.ok(oscSysParamService.getBaseMapper().selectList(null));
	}



	/**
	 * 查询汇率(开启缓存)
	 * @return R
	 */
	@ApiOperation(value = "查询汇率(开启缓存)", notes = "查询汇率(开启缓存)")
	@PostMapping("/exchange_rate_by_cache" )
	public R queryExchangeRate( ) {
		return R.ok(oscSysParamService.queryExchangeRate());
	}

	/**
	 * 查询汇率
	 * @return R
	 */
	@ApiOperation(value = "查询汇率", notes = "查询汇率")
	@PostMapping("/exchange_rate" )
	public R queryExchangeRate(@RequestBody @Valid QueryExchangeRateDTO dto) {
		return R.ok(oscSysParamService.queryExchangeRate(dto));
	}





}
