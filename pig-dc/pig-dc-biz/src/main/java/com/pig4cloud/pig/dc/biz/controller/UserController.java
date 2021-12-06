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
import com.pig4cloud.pig.dc.api.dto.WechatLoginDto2;
import com.pig4cloud.pig.dc.biz.service.impl.AuthServiceImpl;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author lengleng
 * @date 2019/2/1
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Api(value = "UserController", tags = "用户模块")
@Deprecated
public class UserController {

	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();


	private final RemoteOpenUserService remoteOpenUserService;
	private final AuthServiceImpl authService;


	@PostMapping("/auth/mini_login")
	public R wecahtLogin(@RequestParam String code) {

		return R.ok(authService.miniLogin(code));
	}



	@PostMapping("/auth/wechat_login")
	public R wecahtLogin(
					  @RequestParam String code,
					  @RequestParam(required = false) String iv,
					  @RequestParam(required = false) String encryptedData,
	                  @RequestBody WechatLoginDto2 dto
	) {

		return R.ok(authService.wecahtLogin(code,iv,encryptedData,dto));
	}




	@ApiIgnore
	@PostMapping("/auth/regist")
	public R regist(@RequestParam String username) {
		return R.ok(authService.regist(username));
	}








}
