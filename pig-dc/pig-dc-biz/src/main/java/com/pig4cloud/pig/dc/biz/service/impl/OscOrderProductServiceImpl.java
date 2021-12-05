package com.pig4cloud.pig.dc.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.dc.api.entity.OscOrderProduct;
import com.pig4cloud.pig.dc.biz.mapper.OscOrderProductMapper;
import com.pig4cloud.pig.dc.biz.service.IOscOrderProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单-产品 服务实现类
 * </p>
 *
 * @author chenlei
 * @since 2021-12-05
 */
@Service
@RequiredArgsConstructor
public class OscOrderProductServiceImpl extends ServiceImpl<OscOrderProductMapper, OscOrderProduct> implements IOscOrderProductService {

}
