package com.pig4cloud.pig.dc.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.dc.api.dto.QueryExchangeRateDTO;
import com.pig4cloud.pig.dc.api.entity.OscSysParam;
import com.pig4cloud.pig.dc.api.entity.OscSysParamMemberPrice;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Map;

/**
 * <p>
 * 系统参数 服务类
 * </p>
 *
 * @author chenlei
 * @since 2021-12-09
 */
//@Validated
public interface IOscSysParamService extends IService<OscSysParam> {

	/**
	 * @Name:
	 * @Description: 设置会员价格
	 * @Param:
	 * @return:
	 * @Author: LeiChen
	 * @Date:2021/12/9 14:37
	 *
	 * */
	Integer resetParam(String key,OscSysParamMemberPrice price);

	/**
	 * @Name:
	 * @Description: 查询汇率
	 * @Param:
	 * @return:
	 * @Author: LeiChen
	 * @Date:2021/12/9 15:44
	 *
	 * */
	@Cacheable(cacheNames = {"queryExchangeRate"}, key="#dto.params")
	Map<String,String> queryExchangeRate(@Valid QueryExchangeRateDTO dto);


	/**
	 * @Name:
	 * @Description: 查询汇率
	 * @Param:
	 * @return:
	 * @Author: LeiChen
	 * @Date:2021/12/9 15:44
	 *
	 * */
	Map<String,String> queryExchangeRate( );
}
