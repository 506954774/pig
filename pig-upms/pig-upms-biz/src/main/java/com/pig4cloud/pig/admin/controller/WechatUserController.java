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

package com.pig4cloud.pig.admin.controller;

import com.pig4cloud.pig.admin.api.dto.UserDTO;
import com.pig4cloud.pig.admin.service.SysUserService;
import com.pig4cloud.pig.common.core.util.R;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.*;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author lengleng
 * @date 2019/2/1
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/wechat")
@Api(value = "wechat", tags = "微信管理模块")
public class WechatUserController {

	private final SysUserService userService;




	/**
	 * 小程序登录
	 * @param userDto 用户信息
	 * @return success/false
	 */
	@PostMapping("/login")
	public R user(@RequestBody UserDTO userDto) {

		return R.ok(login());
	}


	/**
	 * @Name: login
	 * @Description: 手动调用登录接口
	 * @Param: []
	 * @return: org.springframework.security.oauth2.common.OAuth2AccessToken
	 * @Author: LeiChen
	 * @Date:2021/11/8 9:44
	 *
	 * */
	private OAuth2AccessToken login() {

		ResponseEntity<OAuth2AccessToken> responseEntity=null;

		// 请求地址
		String url = "http://127.0.0.1:8888/auth/oauth/token";


		// 请求头设置,x-www-form-urlencoded格式的数据
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Authorization","Basic cGlnOnBpZw==");

		//提交参数设置
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("username","admin");
		map.add("password","123456");
		map.add("randomStr","19721636296160111");
		map.add("code","1");
		map.add("grant_type","password");
		map.add("scope","server");

		// 组装请求体
		HttpEntity<MultiValueMap<String, String>> request =
				new HttpEntity<MultiValueMap<String, String>>(map, headers);

		RestTemplate restTemplate =new RestTemplate();


		// 发送post请求，并打印结果，以String类型接收响应结果JSON字符串
		responseEntity = restTemplate.postForEntity(url, request, OAuth2AccessToken.class);

		System.out.println(responseEntity);

		return responseEntity.getBody();
	}

	private ResponseEntity<OAuth2AccessToken> login3(){
		ResponseEntity<OAuth2AccessToken> responseEntity=null;
		try {

//LFEt60GoMTj5%2FmQQ7HjJeA%3D%3D
			//rKu1/348LvKp0rsVC06eCA==
			String url = "http://127.0.0.1:8888/auth/oauth/token?" +
					"username=admin&password=LFEt60GoMTj5%2FmQQ7HjJeA%3D%3D&randomStr=87841636298011769&code=1&grant_type=password&scope=server";
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization","Basic cGlnOnBpZw==");
			headers.set("Accept","application/json, text/plain, */*");
			headers.set("Accept-Encoding","gzip, deflate, br");
			headers.set("Content-Length","0");
			headers.set("isToken","false");
			headers.set("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");

			HttpEntity<JSONObject> httpEntity = new HttpEntity(headers);
			RestTemplate restTemplate = new RestTemplate();
			responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, OAuth2AccessToken.class);
			//输出接口所返回过来的值
			System.out.println(responseEntity.getBody());
			return responseEntity;
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			return responseEntity;
		}
	}

	private OAuth2AccessToken login2(){
		ResponseEntity<OAuth2AccessToken> responseEntity=new ResponseEntity<OAuth2AccessToken>(HttpStatus.BAD_REQUEST);
		try {

			 RestTemplate restTemplate =new RestTemplate();

			//跨域访问表头
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization","Basic cGlnOnBpZw==");
			headers.add("isToken","false");
			headers.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");

			//接口地址
			String url = "http://127.0.0.1:8888/auth/oauth/token";
			LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
			//利用multiValueMap插入需要传输的数据
			map.add("username","admin");
			map.add("password","123456");
			map.add("randomStr","19721636296160111");
			map.add("code","1");
			map.add("grant_type","password");
			map.add("scope","server");

			//访问接口并获取返回值
			 responseEntity = restTemplate.getForEntity(url, OAuth2AccessToken.class,map);
			//输出接口所返回过来的值
			System.out.println(responseEntity.getBody());
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			return responseEntity.getBody();
		}
	}



}
