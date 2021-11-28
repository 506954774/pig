package com.pig4cloud.pig.dc.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.dc.api.entity.OscMajor;
import com.pig4cloud.pig.dc.biz.mapper.OscMajorMapper;
import com.pig4cloud.pig.dc.biz.service.IOscMajorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 专业 服务实现类
 * </p>
 *
 * @author chenlei
 * @since 2021-11-28
 */
@Service
@RequiredArgsConstructor
public class OscMajorServiceImpl extends ServiceImpl<OscMajorMapper, OscMajor> implements IOscMajorService {

}
