package com.pig4cloud.pig.dc.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.dc.api.entity.OscCollege;
import com.pig4cloud.pig.dc.biz.mapper.OscCollegeMapper;
import com.pig4cloud.pig.dc.biz.service.IOscCollegeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 学院 服务实现类
 * </p>
 *
 * @author chenlei
 * @since 2021-11-28
 */
@Service
@RequiredArgsConstructor
public class OscCollegeServiceImpl extends ServiceImpl<OscCollegeMapper, OscCollege> implements IOscCollegeService {

}
