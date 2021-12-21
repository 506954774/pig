package com.pig4cloud.pig.dc.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.dc.api.entity.OscEducationLevel;
import com.pig4cloud.pig.dc.biz.mapper.OscEducationLevelMapper;
import com.pig4cloud.pig.dc.biz.service.IOscEducationLevelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 留学,学历阶段 服务实现类
 * </p>
 *
 * @author chenlei
 * @since 2021-12-21
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class OscEducationLevelServiceImpl extends ServiceImpl<OscEducationLevelMapper, OscEducationLevel> implements IOscEducationLevelService {

}
