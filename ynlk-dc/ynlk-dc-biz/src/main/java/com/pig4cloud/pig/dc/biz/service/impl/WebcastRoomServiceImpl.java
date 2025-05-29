package com.pig4cloud.pig.dc.biz.service.impl;

import com.pig4cloud.pig.dc.api.dto.PlaybackResponse;
import com.pig4cloud.pig.dc.api.dto.WebcastPageQo;
import com.pig4cloud.pig.dc.api.dto.WebcastPlaybackPageQo;
import com.pig4cloud.pig.dc.api.dto.WebcastRoomResponse;
import com.pig4cloud.pig.dc.api.feign.WechatApiFeignClient;
import com.pig4cloud.pig.dc.biz.config.WechatConfig;
import com.pig4cloud.pig.dc.biz.mapper.OscCollegeMapper;
import com.pig4cloud.pig.dc.biz.mapper.OscMajorMapper;
import com.pig4cloud.pig.dc.biz.mapper.OscUserInfoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

/**
 * <p>
 *     直播间
 * </p>
 *
 * @author chenlei
 * @since 2021-11-23
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WebcastRoomServiceImpl   {

	private final OscUserInfoMapper oscUserInfoMapper;
	private final OscMajorMapper oscMajorMapper;
	private final OscCollegeMapper oscCollegeMapper;

	private final WebcastRoomTokenServiceImpl webcastRoomTokenService;

	private final WechatConfig wechatConfig;

	private final WechatApiFeignClient wechatApiFeignClient;



	/**
	 * @Name:
	 * @Description: 调用微信的接口,获取直播间列表
	 * @Param:
	 * @return:
	 * @Author: LeiChen
	 * @Date:2021/12/14 11:44
	 *
	 * */
	public WebcastRoomResponse queryWebcastRoomPage(@RequestBody @Valid  WebcastPageQo dto){
		return wechatApiFeignClient.getWebcastRoomPage(webcastRoomTokenService.queryWecahtAccessToken(),dto);
	}


	/**
	 * @Name:
	 * @Description: 调用微信的接口,获取直播回放列表
	 * @Param:
	 * @return:
	 * @Author: LeiChen
	 * @Date:2021/12/14 14:26
	 *
	 * */
	public PlaybackResponse getPlayBackPage(@RequestBody @Valid  WebcastPlaybackPageQo dto) {
		return wechatApiFeignClient.getPlayBackPage(webcastRoomTokenService.queryWecahtAccessToken(),dto);
	}
}
