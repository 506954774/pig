package com.pig4cloud.pig.dc.biz.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.dc.api.dto.QueryPageDTO;
import com.pig4cloud.pig.dc.api.entity.OscBanner;
import com.pig4cloud.pig.dc.biz.mapper.OscBannerMapper;
import com.pig4cloud.pig.dc.biz.service.IOscBannerService;
import lombok.RequiredArgsConstructor;
import org.apache.http.util.TextUtils;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

/**
 * <p>
 * 轮播图 服务实现类
 * </p>
 *
 * @author chenlei
 * @since 2021-11-23
 */
@Service
@RequiredArgsConstructor
public class OscBannerServiceImpl extends ServiceImpl<OscBannerMapper, OscBanner> implements IOscBannerService {

	@Override
	public Page<OscBanner> page(@Valid QueryPageDTO dto) {
		if(dto==null){
			dto=new QueryPageDTO();
		}
		Page page=new Page();
		page.setCurrent(dto.getCurrent());
		page.setSize(dto.getSize());
		final QueryPageDTO finalDto = dto;
		Page page1 =  getBaseMapper().selectPage(
				page, Wrappers.<OscBanner>query().lambda()
						/*.and(!TextUtils.isEmpty(dto.getKeyword()),wrapper -> wrapper. like(OscBanner::getTitle, finalDto.getKeyword())
								.or(). like(OscBanner::get, finalDto.getKeyword()))*/
						.like(!TextUtils.isEmpty(dto.getKeyword()),OscBanner::getTitle, dto.getKeyword())
						.orderByDesc(OscBanner::getBannerSort)

		);
		return page1;
	}
}
