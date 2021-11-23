package com.pig4cloud.pig.dc.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.dc.api.entity.OscBanner;
import com.pig4cloud.pig.dc.biz.mapper.OscBannerMapper;
import com.pig4cloud.pig.dc.biz.service.IOscBannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
