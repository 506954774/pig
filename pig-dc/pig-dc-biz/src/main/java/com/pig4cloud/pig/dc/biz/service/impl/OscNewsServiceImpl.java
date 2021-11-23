package com.pig4cloud.pig.dc.biz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.dc.api.entity.OscNews;
import com.pig4cloud.pig.dc.biz.mapper.OscNewsMapper;
import com.pig4cloud.pig.dc.biz.service.IOscNewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 新闻 服务实现类
 * </p>
 *
 * @author chenlei
 * @since 2021-11-23
 */
@Service
@RequiredArgsConstructor
public class OscNewsServiceImpl extends ServiceImpl<OscNewsMapper, OscNews> implements IOscNewsService {

}
