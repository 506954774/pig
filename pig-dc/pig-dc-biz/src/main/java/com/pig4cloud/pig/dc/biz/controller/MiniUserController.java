/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pig4cloud.pig.dc.biz.controller;

import com.pig4cloud.pig.admin.api.feign.RemoteOpenUserService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.dc.api.dto.QueryUniversityPageDTO;
import com.pig4cloud.pig.dc.api.entity.OscUserInfo;
import com.pig4cloud.pig.dc.biz.service.IOscUserInfoService;
import com.pig4cloud.pig.dc.biz.service.impl.AuthServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * @author lengleng
 * @date 2019/2/1
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mini/user")
@Api(value = "MiniUserController", tags = "小程序-用户模块")
public class MiniUserController {

	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();


	private final RemoteOpenUserService remoteOpenUserService;
	private final AuthServiceImpl authService;
	private final IOscUserInfoService iOscUserInfoService;

	@ApiOperation(value = "小程序登录", notes = "小程序登录")
	@PostMapping("/auth/mini_login")
	public R miniLogin(@RequestParam String code) {
		return R.ok(authService.miniLogin(code));
	}


	@ApiOperation(value = "提交微信绑定的手机号", notes = "提交微信绑定的手机号")
	@PostMapping("/auth/bind_phone")
	public R bindPhone(
					  @RequestParam String code,
					  @RequestParam(required = false) String iv,
					  @RequestParam(required = false) String encryptedData
	) {

		return R.ok(authService.bindPhone(code,iv,encryptedData ));
	}


	@ApiOperation(value = "根据用户id查询个人信息", notes = "根据用户id查询个人信息")
	@GetMapping("/info/{id}")
	public R details(@PathVariable Integer id) {
		return R.ok(iOscUserInfoService.details(id));
	}


	@ApiOperation(value = "更新用户信息", notes = "更新用户信息")
	@PostMapping("/update")
	public R update(@RequestBody @Valid OscUserInfo dto) {
		return R.ok(iOscUserInfoService.getBaseMapper().updateById(dto));
	}


	@ApiIgnore
	@PostMapping("/auth/regist")
	public R regist(@RequestParam String username) {
		return R.ok(authService.regist(username));
	}








}
