package com.pig4cloud.pig.dc.biz.service.impl;
import com.google.common.collect.Lists;

import java.util.*;

import cn.felord.payment.wechat.v3.WechatApiProvider;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.dc.api.dto.QueryExchangeRateDTO;
import com.pig4cloud.pig.dc.api.entity.ExchangeRateResponse;
import com.pig4cloud.pig.dc.api.entity.OscSysParam;
import com.pig4cloud.pig.dc.api.entity.OscSysParamMemberPrice;
import com.pig4cloud.pig.dc.biz.config.Constant;
import com.pig4cloud.pig.dc.biz.config.ExchangeRateApiConfig;
import com.pig4cloud.pig.dc.biz.feign.ExchangeRateApiFeignClient;
import com.pig4cloud.pig.dc.biz.mapper.OscSysParamMapper;
import com.pig4cloud.pig.dc.biz.service.IOscSysParamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

/**
 * <p>
 * 系统参数 服务实现类
 * </p>
 *
 * @author chenlei
 * @since 2021-12-09
 */
@Validated
@Slf4j
@Service
@RequiredArgsConstructor
public class OscSysParamServiceImpl extends ServiceImpl<OscSysParamMapper, OscSysParam> implements IOscSysParamService {

	//private final RestTemplate restTemplate;
	private final ExchangeRateApiConfig exchangeRateApiConfig;
	private final ExchangeRateApiFeignClient exchangeRateApiFeignClient;
	//private final IOscSysParamService _this; //注入自己会导致循环依赖


	@Override
	public Integer resetParam(String key,OscSysParamMemberPrice price) {
		OscSysParam oscSysParam = getBaseMapper().selectOne(Wrappers.<OscSysParam>query().lambda().eq(OscSysParam::getParamKey,key));
		if(oscSysParam==null){
			OscSysParam bean=new OscSysParam();
			bean.setParamKey(key);
			bean.setParamValue(price.getParamValue());
			bean.setCreateTime(new Date());
			bean.setCreateBy(SecurityUtils.getUser().getId()+"");
			getBaseMapper().insert(bean);
			return bean.getId();
		}
		else{
			oscSysParam.setParamValue(price.getParamValue());
			getBaseMapper().updateById(oscSysParam);
			return oscSysParam.getId();
		}
	}

	@Cacheable(cacheNames = {"queryExchangeRate"}, key="#dto.params")
	@Override
	public Map<String, String> queryExchangeRate(@Valid QueryExchangeRateDTO dto) {

		//key:币种,value:对人民币的汇率
		Map<String, String> result=new HashMap<>();

		for (String money:dto.getParams()){
			//调用接口,查询汇率,每次接口只能查一个
			ExchangeRateResponse response = exchangeRateApiFeignClient.queryChannels("APPCODE " + exchangeRateApiConfig.getAppCode(), money, "1", "CNY");
			log.info("第三方汇率接口返回值,{}",response);

			if(response!=null){
				result.put(money,response.getShowapi_res_body().getMoney());
			}
		}

		return result;
	}

	@Override
	public Map<String, String> queryExchangeRate() {
		QueryExchangeRateDTO dto=new QueryExchangeRateDTO();
		//ArrayList<String> strings=new ArrayList<>();
		//strings.addAll(Arrays.asList(exchangeRateApiConfig.getCurrency().split(Constant.SEPCTOR)));
		dto.setParams(Arrays.asList(exchangeRateApiConfig.getCurrency().split(Constant.SEPCTOR)));
		//由于@Cacheable是通过动态代理执行的,内部调用不会走aop,此处要么另外调用,要么拿到bean再调用
		return  queryExchangeRate( dto);
		//return queryExchangeRate( dto);
	}

	/*
	@Override
	public Map<String, String> queryExchangeRate(QueryExchangeRateDTO dto) {

		//key:币种,value:对人民币的汇率
		Map<String, String> result=new HashMap<>();

		for (String money:dto.getParams()){
			//调用接口,查询汇率,每次接口只能查一个

			ExchangeRateResponse responseEntity = exchangeRateApiFeignClient.queryChannels(
					"APPCODE " + exchangeRateApiConfig.getAppCode(),
					money,
					"1",
					"CNY");
			log.info("第三方汇率接口返回值,{}",responseEntity);

			if(responseEntity!=null){
				result.put(money,responseEntity.getShowapiResBody().getMoney());
			}




		}

		return result;
	}
*/
/*
	@Override
	public Map<String, String> queryExchangeRate(QueryExchangeRateDTO dto) {

		//key:币种,value:对人民币的汇率
		Map<String, String> result=new HashMap<>();

		for (String money:dto.getParams()){
			//调用接口,查询汇率,每次接口只能查一个

			ResponseEntity<ExchangeRateResponse> responseEntity=null;

			// 请求地址
			String url = "https://ali-waihui.showapi.com/waihui-transform?fromCode=$&money=1&toCode=CNY";
			url=url.replace("$",money);

			log.info("汇率转换,url:{}",url);

			// 请求头设置,x-www-form-urlencoded格式的数据
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.set("Authorization","APPCODE "+exchangeRateApiConfig.getAppCode());
			log.info("汇率转换,getAppCode:{}",exchangeRateApiConfig.getAppCode());

			//提交参数设置
			MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

			// 组装请求体
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

			// 发送post请求
			RestTemplate restTemplate =new RestTemplate();
			responseEntity = restTemplate.getForEntity(url,   ExchangeRateResponse.class);

			if(responseEntity!=null){
				result.put(money,responseEntity.getBody().getShowapiResBody().getMoney());
			}



		}

		return result;
	}
*/


	/*@Cacheable(cacheNames = {"queryExchangeRate"}, key="#dto.params")
	@Override
	public Map<String, String> queryExchangeRate(@Valid QueryExchangeRateDTO dto) {

		//key:币种,value:对人民币的汇率
		Map<String, String> result=new HashMap<>();

		for (String money:dto.getParams()){
			//调用接口,查询汇率,每次接口只能查一个

			// 请求地址
			String url = "https://ali-waihui.showapi.com/waihui-transform?fromCode=$&money=1&toCode=CNY";
			url=url.replace("$",money);
			log.info("汇率转换,url:{}",url);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.set("Authorization","APPCODE "+exchangeRateApiConfig.getAppCode());
			log.info("汇率转换,getAppCode:{}",exchangeRateApiConfig.getAppCode());
			HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

			RestTemplate restTemplate =new RestTemplate();
			ResponseEntity<ExchangeRateResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, ExchangeRateResponse.class);
			log.info("第三方汇率接口返回值,{}",responseEntity);

			if(responseEntity!=null){
				result.put(money,responseEntity.getBody().getShowapi_res_body().getMoney());
			}

		}

		return result;
	}*/
}
