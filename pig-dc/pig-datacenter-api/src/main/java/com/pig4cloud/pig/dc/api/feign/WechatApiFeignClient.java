package com.pig4cloud.pig.dc.api.feign;

import com.pig4cloud.pig.dc.api.dto.PlaybackResponse;
import com.pig4cloud.pig.dc.api.dto.WebcastPageQo;
import com.pig4cloud.pig.dc.api.dto.WebcastPlaybackPageQo;
import com.pig4cloud.pig.dc.api.dto.WebcastRoomResponse;
import com.pig4cloud.pig.dc.api.entity.ExchangeRateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * WechatApiFeignClient
 * 微信公众号api
 * 责任人:  ChenLei
 * 修改人： ChenLei
 * 创建/修改时间: 2021/12/9 16:42
 * Copyright :  版权所有
 **/

@FeignClient(url="https://api.weixin.qq.com",name = "wechatApiFeignClient"   )
public interface WechatApiFeignClient {



	@GetMapping("/cgi-bin/token")
	ExchangeRateResponse getAccessToken(
									   @RequestParam("grant_type")String grant_type,
									   @RequestParam("appid") String appid,
									   @RequestParam("secret") String secret);

	@PostMapping("/wxa/business/getliveinfo")
	WebcastRoomResponse getWebcastRoomPage(
						@RequestParam("access_token") String access_token,
						@RequestBody WebcastPageQo cloudDeviceGbAddBo);

	@PostMapping("/wxa/business/getliveinfo")
	PlaybackResponse getPlayBackPage(
						@RequestParam("access_token") String access_token,
						@RequestBody WebcastPlaybackPageQo dto);


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
