package com.pig4cloud.pig.dc.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.dc.api.dto.AddEnrollDTO;
import com.pig4cloud.pig.dc.api.entity.OscBanner;
import com.pig4cloud.pig.dc.api.entity.OscEnrollInfo;
import com.pig4cloud.pig.dc.api.entity.OscMajor;
import com.pig4cloud.pig.dc.api.entity.OscUserInfo;
import com.pig4cloud.pig.dc.biz.exceptions.BizException;
import com.pig4cloud.pig.dc.biz.mapper.OscCollegeMapper;
import com.pig4cloud.pig.dc.biz.mapper.OscEnrollInfoMapper;
import com.pig4cloud.pig.dc.biz.mapper.OscMajorMapper;
import com.pig4cloud.pig.dc.biz.mapper.OscUserInfoMapper;
import com.pig4cloud.pig.dc.biz.service.IOscEnrollInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 申请 服务实现类
 * </p>
 *
 * @author chenlei
 * @since 2021-11-23
 */
@Service
@RequiredArgsConstructor
public class OscEnrollInfoServiceImpl extends ServiceImpl<OscEnrollInfoMapper, OscEnrollInfo> implements IOscEnrollInfoService {

	private final OscUserInfoMapper oscUserInfoMapper;
	private final OscMajorMapper oscMajorMapper;
	private final OscCollegeMapper oscCollegeMapper;


	@Override
	public Integer saveInfo(AddEnrollDTO dto) {
		/*OscUserInfo oscUserInfo = oscUserInfoMapper.selectById(dto.getUserId());
		if(oscUserInfo==null){
			throw new BizException("用户不存在或者已被删除");
		}*/
		/*OscMajor oscMajor = oscMajorMapper.selectById(dto.getMajorId());
		if(oscMajor==null){
			throw new BizException("专业不存在或者已被删除");
		}*/
		OscEnrollInfo entity= BeanUtil.copyProperties(dto,OscEnrollInfo.class);
		//entity.setUserTel(oscUserInfo.getPhone());
		entity.setCreateTime(new Date());
		getBaseMapper().insert(entity);
		return entity.getId();
	}
}
