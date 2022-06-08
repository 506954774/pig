package com.pig4cloud.pig.dc.biz.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.dc.api.dto.MemberExcelVo;
import com.pig4cloud.pig.dc.api.dto.QueryPageDTO;
import com.pig4cloud.pig.dc.api.entity.OscUserInfo;
import com.pig4cloud.pig.dc.biz.mapper.OscUserInfoMapper;
import com.pig4cloud.pig.dc.biz.service.IOscUserInfoService;
import lombok.RequiredArgsConstructor;
import org.apache.http.util.TextUtils;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author chenlei
 * @since 2021-11-26
 */
@Service
@RequiredArgsConstructor
public class OscUserInfoServiceImpl extends ServiceImpl<OscUserInfoMapper, OscUserInfo> implements IOscUserInfoService {


	@Override
	public OscUserInfo details(Integer id) {
		return getBaseMapper().selectById(id);
	}

	@Override
	public List<MemberExcelVo> exportMemberList(@Valid QueryPageDTO dto) {
		if(dto==null){
			dto=new QueryPageDTO();
		}
		final QueryPageDTO finalDto = dto;
		List<OscUserInfo> oscUserInfos = getBaseMapper().selectList(
				Wrappers.<OscUserInfo>query().lambda()
						.and(!TextUtils.isEmpty(dto.getKeyword()),

								wrapper1 -> wrapper1.like(OscUserInfo::getRealname, finalDto.getKeyword())
										.or(

												wrapper -> wrapper
														.like(OscUserInfo::getNickname, finalDto.getKeyword())
														.or().like(OscUserInfo::getPhone, finalDto.getKeyword()
														)
										)
						)
						//.like(!TextUtils.isEmpty(dto.getKeyword()),OscOffer::getStuName, dto.getKeyword())
						.orderByDesc(OscUserInfo::getCreateTime)

		);
		if(CollectionUtils.isEmpty(oscUserInfos)){
			return null;
		}
		else {
			return oscUserInfos.stream().map(bean->
			{
				MemberExcelVo vo=new MemberExcelVo();
				vo.setPhone(bean.getPhone());
				vo.setRegisterTime(DateUtil.formatDateTime(bean.getCreateTime()));
				return vo;
			}).collect(Collectors.toList());
		}
	}
}
