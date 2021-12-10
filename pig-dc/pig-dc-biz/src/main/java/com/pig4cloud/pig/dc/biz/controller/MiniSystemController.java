package com.pig4cloud.pig.dc.biz.controller;

import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.dc.api.dto.QueryExchangeRateDTO;
import com.pig4cloud.pig.dc.biz.config.Constant;
import com.pig4cloud.pig.dc.biz.config.ExchangeRateApiConfig;
import com.pig4cloud.pig.dc.biz.service.IOscAdministrativeDivisionService;
import com.pig4cloud.pig.dc.biz.service.IOscSysParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

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
	private final ExchangeRateApiConfig exchangeRateApiConfig;


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

		QueryExchangeRateDTO dto=new QueryExchangeRateDTO();
		//ArrayList<String> strings=new ArrayList<>();
		//strings.addAll(Arrays.asList(exchangeRateApiConfig.getCurrency().split(Constant.SEPCTOR)));
		dto.setParams(Arrays.asList(exchangeRateApiConfig.getCurrency().split(Constant.SEPCTOR)));
		//由于@Cacheable是通过动态代理执行的,内部调用不会走aop
		//return  _this.queryExchangeRate( dto);
		return R.ok(oscSysParamService.queryExchangeRate(dto));
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
