package com.pig4cloud.pig.dc.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.dc.api.entity.OscEnrollInfo;
import com.pig4cloud.pig.dc.biz.mapper.OscEnrollInfoMapper;
import com.pig4cloud.pig.dc.biz.service.IOscEnrollInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
