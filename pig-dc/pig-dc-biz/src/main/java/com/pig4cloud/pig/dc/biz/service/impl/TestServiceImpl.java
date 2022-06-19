package com.pig4cloud.pig.dc.biz.service.impl;

import com.pig4cloud.pig.admin.api.entity.SysLog;
import com.pig4cloud.pig.admin.api.feign.RemoteLogService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.dc.api.dto.PlaybackResponse;
import com.pig4cloud.pig.dc.api.dto.WebcastPageQo;
import com.pig4cloud.pig.dc.api.dto.WebcastPlaybackPageQo;
import com.pig4cloud.pig.dc.api.dto.WebcastRoomResponse;
import com.pig4cloud.pig.dc.api.entity.OscNews;
import com.pig4cloud.pig.dc.api.feign.WechatApiFeignClient;
import com.pig4cloud.pig.dc.biz.config.WechatConfig;
import com.pig4cloud.pig.dc.biz.mapper.OscCollegeMapper;
import com.pig4cloud.pig.dc.biz.mapper.OscMajorMapper;
import com.pig4cloud.pig.dc.biz.mapper.OscNewsMapper;
import com.pig4cloud.pig.dc.biz.mapper.OscUserInfoMapper;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Date;

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
public class TestServiceImpl {

	private final RemoteLogService remoteLogService;
	private final OscNewsMapper oscNewsMapper;

	//分布式事务
	@GlobalTransactional
	@Transactional(rollbackFor = Exception.class)
	public R seataTest(@PathVariable Integer id ) {

		log.info("seataTest,service层执行");

		//本地服务插入一条数据
		OscNews news=new OscNews();
		news.setTitle("test");
		news.setWebUrl("test");
		news.setCreateTime(new Date());
		news.setCreateBy("test");
		news.setIocnUrl("test");
		news.setNewsSort(0);
		oscNewsMapper.insert(news);

		//使用feign远程服务插入一条数据
		SysLog logs=new SysLog();
		logs.setType("0");
		logs.setTitle("test");
		logs.setRemoteAddr("test");
		logs.setUserAgent("test");
		logs.setRequestUri("test");
		logs.setMethod("test");
		logs.setParams("test");

		remoteLogService.saveLog(logs, com.pig4cloud.pig.common.core.constant.SecurityConstants.FROM_IN);

		//传值,抛出异常,触发seata分布式事务回滚
		int a=1/id;

		return R.ok("测试seata");
	}
}
