package com.pig4cloud.pig.dc.biz.feign;

import com.pig4cloud.pig.dc.api.entity.ExchangeRateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * ExchangeRateApiFeignClient
 * 责任人:  ChenLei
 * 修改人： ChenLei
 * 创建/修改时间: 2021/12/9 16:42
 * Copyright :  版权所有
 **/

@FeignClient(url="https://ali-waihui.showapi.com",name = "exchangeRateApiFeignClient"   )
public interface ExchangeRateApiFeignClient {



	@GetMapping("/waihui-transform")
	ExchangeRateResponse queryChannels(@RequestHeader("Authorization") String appCode,
									   @RequestParam("fromCode")String fromCode,
									   @RequestParam("money") String money,
									   @RequestParam("toCode") String toCode);

/*	@PostMapping("/v1/{user_id}/devices/holosens")
	Response cloudDeviceHolosens(@RequestHeader("Access-Token")  String accessToken,@PathVariable("user_id") String cloudUserId,
								 @RequestBody CloudDeviceHoloAddBo cloudDeviceHoloAddBo);

	@PostMapping("/v1/{user_id}/devices/gb/batch-add")
	Map cloudGBBatchAdd(@RequestHeader("Access-Token")  String accessToken,@PathVariable("user_id") String cloudUserId,
						@RequestBody CloudDeviceGbAddBo cloudDeviceGbAddBo);


	@GetMapping("/v1/{user_id}/channels")
	Map queryPageChannels(@RequestHeader("Access-Token") String accessToken,@PathVariable("user_id")String cloudUserId,
						  @RequestParam("offset") Integer offset,@RequestParam("limit") Integer limit);*/
}
