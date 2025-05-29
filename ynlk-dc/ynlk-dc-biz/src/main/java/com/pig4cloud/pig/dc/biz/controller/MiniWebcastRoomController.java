package com.pig4cloud.pig.dc.biz.controller;

import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.dc.api.dto.WebcastPageQo;
import com.pig4cloud.pig.dc.api.dto.WebcastPlaybackPageQo;
import com.pig4cloud.pig.dc.biz.service.IOscAdministrativeDivisionService;
import com.pig4cloud.pig.dc.biz.service.IOscAreaService;
import com.pig4cloud.pig.dc.biz.service.impl.WebcastRoomServiceImpl;
import com.pig4cloud.pig.dc.biz.service.impl.WebcastRoomTokenServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * @author LeiChen
 * @version 1.0
 * @date 2021/5/21 12:37
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mini/webcast_room")
@Api(value = "MiniWebcastRoomController", tags = "小程序-直播间接口")
public class MiniWebcastRoomController {

	private final IOscAreaService iOscAreaService;
	private final IOscAdministrativeDivisionService iOscAdministrativeDivisionService;
	private final WebcastRoomServiceImpl webcastRoomService;



	private final WebcastRoomTokenServiceImpl webcastRoomTokenService;


	/**
	 *  查询token
	 *
	 * @return
	 */
	@ApiIgnore
	@ApiOperation(value = "查询token", notes = "查询token")
	@GetMapping("/access_token")
	public R queryWecahtAccessToken() {
		return R.ok(webcastRoomTokenService.queryWecahtAccessToken());
	}


	/**
	 *  查询直播间列表分页
	 *
	 * @return
	 */
	@ApiOperation(value = "查询直播间列表分页", notes = "https://developers.weixin.qq.com/miniprogram/dev/platform-capabilities/industry/liveplayer/studio-api.html#2")
	@PostMapping("/page")
	public R page(@RequestBody @Valid WebcastPageQo dto) {
		return R.ok(webcastRoomService.queryWebcastRoomPage(dto));
	}



	/**
	 *  查询直播间的回放列表
	 *
	 * @return
	 */
	@ApiOperation(value = "查询直播间的回放列表", notes = "https://developers.weixin.qq.com/miniprogram/dev/platform-capabilities/industry/liveplayer/studio-api.html#2")
	@PostMapping("/playback_page")
	public R getPlayBackPage(@RequestBody @Valid WebcastPlaybackPageQo dto) {
		return R.ok(webcastRoomService.getPlayBackPage(dto));
	}


}
