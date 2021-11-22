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

package com.pig4cloud.pig.dc.biz.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import com.pig4cloud.pig.admin.api.dto.UserDTO;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.pig4cloud.pig.admin.api.feign.RemoteOpenUserService;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.dc.api.dto.WechatLoginDto;
import com.pig4cloud.pig.dc.biz.config.Constant;
import com.pig4cloud.pig.dc.biz.exceptions.BizException;
import com.ulisesbocchio.jasyptspringboot.encryptor.DefaultLazyEncryptor;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.http.util.TextUtils;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lengleng
 * @since 2019/2/1
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl {

	/**
	 * authHost: http://120.27.135.25:9999
	 *
	 * */

	@Value("${authHost}")
	private String mAuthHost;

	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();


	private final RemoteOpenUserService remoteOpenUserService;

	private final MyWxServiceImpl myWxService;


	/**
	 * @Name:
	 * @Description: 微信授权,获取手机号,然后登录
	 * @Param:
	 * @return:
	 * @Author: LeiChen
	 * @Date:2021/11/21 18:04
	 *
	 * */
	public OAuth2AccessToken wecahtLogin(String code, String iv, String encryptedData) {

		if(TextUtils.isEmpty(code)||TextUtils.isEmpty(iv)||TextUtils.isEmpty(encryptedData)){
			throw new BizException("参数不合法!");
		}

		if(!TextUtils.isEmpty(code)){
			throw new BizException("参数不合法!");
		}

		try {
			iv = URLEncoder.encode(iv,"UTF-8").replace("%3D","=").replace("%2F","/");
			encryptedData = URLEncoder.encode(encryptedData,"UTF-8").replace("%3D","=").replace("%2F","/");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new BizException("参数不合法!");
		}
//        log.info("删除会话信息redis缓存..");
//        log.info("小程序绑定平台用户:iv=" + iv + ",encryptedData=" + encryptedData);
//        log.info("小程序绑定平台用户: session --->" + sessionResult);




		WxMaJscode2SessionResult result = null;
		try {
			result = myWxService.getUserService().getSessionInfo(code);
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
//        log.info("微信小程序响应报文:{}", result);
//        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(WxMaJscode2SessionResult.class));
//        redisTemplate.opsForHash().put("auth:mini:session", code, result);
//        HashMap<String, String> map = new HashMap<>();
//        map.put("unionId",result.getUnionid());
//        map.put("openId",result.getOpenid());
//        map.put("info",result.getUnionid());

//        WxMaService wxMaService = WxMaInitConfigRunner.getMpServices().get(sysProgram.getAppid());
		WxMaUserService userService = myWxService.getUserService();
		WxMaPhoneNumberInfo phoneNumberInfo = userService.getPhoneNoInfo(result.getSessionKey(), encryptedData, iv);



		if (phoneNumberInfo==null|| TextUtils.isEmpty(phoneNumberInfo.getPhoneNumber())) {
			throw new RuntimeException("参数不合法,解密失败!");
		}
		String username=null;
		String password=Constant.PASSWORD;

		username=phoneNumberInfo.getPhoneNumber();

		R<SysUser> admin = remoteOpenUserService.info(username, SecurityConstants.FROM_IN);

		//新增
		if(admin.getData()==null){
			UserDTO user=new UserDTO();
			user.setUsername(username);

			List<Integer> roles=new ArrayList<>();

			// TODO: 2021/11/8 数据库里,先配置一个"普通用户"的角色,id是5
			//这个角色id,注意数据库配置
			roles.add(5);
			user.setRole(roles);
			user.setDeptId(1);

			//选填部分的数据
			user.setPhone(phoneNumberInfo.getPhoneNumber());
			//user.setAvatar(userDto.getAvatar());

			user.setPassword(password);

			//电话号码
			user.setPhone("-1");
			remoteOpenUserService.user(user,SecurityConstants.FROM_IN);
		}
		//已有用户登录
		else{
			username=admin.getData().getUsername();
		}

		return login(username,password);
	}


	public static void main3(String[] args) {
		/*byte[] bytes = "pig".getBytes(StandardCharsets.UTF_8);
		AES aes = new AES(Mode.CBC, Padding.ZeroPadding, bytes, bytes);
		String s = aes.encryptBase64("123456".getBytes(StandardCharsets.UTF_8));
		System.out.println(s);*/

		//这个只是对配置文件的加密解密,是对称的.但是user表里的存的是hash摘要,是无法解密的.而且同一个数据,摘要多次,结果都不一样
		System.setProperty("jasypt.encryptor.password", "pig");
		StringEncryptor stringEncryptor = new DefaultLazyEncryptor(new StandardEnvironment());
		//加密方法
		System.out.println(stringEncryptor.encrypt("123456"));
		//解密方法
		System.out.println(stringEncryptor.decrypt("F9GWtB1u8YLszCfLM41JPQ=="));

		System.out.println(ENCODER.matches("123456","$2a$10$sEA/PlNfwipbNEfdPUyPmOTzSd0mKUbZTEMcDE98eyDVg2h2ihX.C"));
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
	private OAuth2AccessToken login(String userName, String password) {

		ResponseEntity<OAuth2AccessToken> responseEntity=null;

		// 请求地址
		String url = mAuthHost+"/auth/oauth/token";


		// 请求头设置,x-www-form-urlencoded格式的数据
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.set("Authorization","Basic cGlnOnBpZw==");

		//提交参数设置
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("username",userName);
		map.add("password",password);
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

	/**
	 * 小程序用户登录
	 * @param userDto 用户信息
	 * @return success/false
	 */
	@ApiIgnore
	@Transactional
	@ApiOperation(value = "小程序用户登录", notes = "小程序用户登录")
	@PostMapping("/auth/mini_program_login")
	public R miniProgramLogin(@RequestBody WechatLoginDto userDto) {

		log.info("miniProgramLogin,dto:{}",userDto);


		//先根据unionId查出账号

		//没有账号则返回一个code让客户端去授权,获取电话号码

		//根据电话号码,先查看是否有账号信息,有,使用123456作为密码(小程序用户不可修改密码),并调用登录

		//无则新增一个用户信息,然后调用登录
		if (userDto==null|| TextUtils.isEmpty(userDto.getUnionid())) {
			throw new RuntimeException("参数不合法,unionid不可为空!");
		}
		String username=null;
		String password="123456";

		username=userDto.getUnionid();

		R<SysUser> admin = remoteOpenUserService.info(username, SecurityConstants.FROM_IN);

		//新增
		if(admin.getData()==null){
			UserDTO user=new UserDTO();
			user.setUsername(username);

			List<Integer> roles=new ArrayList<>();

			// TODO: 2021/11/8 数据库里,先配置一个"普通用户"的角色,id是5
			//这个角色id,注意数据库配置
			roles.add(5);
			user.setRole(roles);
			user.setDeptId(1);

			//选填部分的数据
			user.setPhone(userDto.getPhone());
			user.setAvatar(userDto.getAvatar());

			user.setPassword(password);

			//电话号码
			user.setPhone("-1");
			remoteOpenUserService.user(user,SecurityConstants.FROM_IN);
		}
		//已有用户登录
		else{
			username=admin.getData().getUsername();
		}

		return R.ok(login(username,password));
	}

}
