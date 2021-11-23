package com.pig4cloud.pig.dc.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.dc.api.entity.OscIndexGuide;
import com.pig4cloud.pig.dc.biz.mapper.OscIndexGuideMapper;
import com.pig4cloud.pig.dc.biz.service.IOscIndexGuideService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 导航 服务实现类
 * </p>
 *
 * @author chenlei
 * @since 2021-11-23
 */
@Service
@RequiredArgsConstructor
public class OscIndexGuideServiceImpl extends ServiceImpl<OscIndexGuideMapper, OscIndexGuide> implements IOscIndexGuideService {

}
