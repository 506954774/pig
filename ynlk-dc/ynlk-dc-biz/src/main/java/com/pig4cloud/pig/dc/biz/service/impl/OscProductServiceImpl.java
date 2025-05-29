package com.pig4cloud.pig.dc.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.dc.api.entity.OscProduct;
import com.pig4cloud.pig.dc.biz.mapper.OscProductMapper;
import com.pig4cloud.pig.dc.biz.service.IOscProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品 服务实现类
 * </p>
 *
 * @author chenlei
 * @since 2021-11-23
 */
@Service
@RequiredArgsConstructor
public class OscProductServiceImpl extends ServiceImpl<OscProductMapper, OscProduct> implements IOscProductService {

}
