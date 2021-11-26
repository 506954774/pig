package com.pig4cloud.pig.dc.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.dc.api.entity.OscUserInfo;
import com.pig4cloud.pig.dc.biz.mapper.OscUserInfoMapper;
import com.pig4cloud.pig.dc.biz.service.IOscUserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
