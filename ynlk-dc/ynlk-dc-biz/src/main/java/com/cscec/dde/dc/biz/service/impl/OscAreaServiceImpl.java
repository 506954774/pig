package com.cscec.dde.dc.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cscec.dde.dc.api.entity.OscArea;
import com.cscec.dde.dc.biz.mapper.OscAreaMapper;
import com.cscec.dde.dc.biz.service.IOscAreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 地区 服务实现类
 * </p>
 *
 * @author chenlei
 * @since 2021-11-28
 */
@Service
@RequiredArgsConstructor
public class OscAreaServiceImpl extends ServiceImpl<OscAreaMapper, OscArea> implements IOscAreaService {

}
