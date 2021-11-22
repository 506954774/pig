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

package com.pig4cloud.pig.admin.api.feign.fallback;

import com.pig4cloud.pig.admin.api.dto.UserDTO;
import com.pig4cloud.pig.admin.api.dto.UserInfo;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.pig4cloud.pig.admin.api.feign.RemoteOpenUserService;
import com.pig4cloud.pig.common.core.util.R;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author lengleng
 * @date 2019/2/1
 */
@Slf4j
@Component
public class RemoteOpenUserServiceFallbackImpl implements RemoteOpenUserService {

	@Setter
	private Throwable cause;



	@Override
	public R user(UserDTO userDto, String from) {
		log.error("feign 新增用户失败:{}", userDto, cause);
		return null;
	}

	/**
	 * 通过用户名查询用户、角色信息
	 * @param username 用户名
	 * @param from 内外标志
	 * @return R
	 */
	@Override
	public R<SysUser> info(String username, String from) {
		log.error("feign 查询用户信息失败:{}", username, cause);
		return null;
	}


}
