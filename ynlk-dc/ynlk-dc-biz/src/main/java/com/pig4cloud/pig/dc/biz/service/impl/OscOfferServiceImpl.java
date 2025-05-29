package com.pig4cloud.pig.dc.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.dc.api.entity.OscOffer;
import com.pig4cloud.pig.dc.biz.mapper.OscOfferMapper;
import com.pig4cloud.pig.dc.biz.service.IOscOfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * offer榜单 服务实现类
 * </p>
 *
 * @author chenlei
 * @since 2021-11-23
 */
@Service
@RequiredArgsConstructor
public class OscOfferServiceImpl extends ServiceImpl<OscOfferMapper, OscOffer> implements IOscOfferService {

}
