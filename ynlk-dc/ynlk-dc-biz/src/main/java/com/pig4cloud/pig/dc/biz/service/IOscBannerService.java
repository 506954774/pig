package com.pig4cloud.pig.dc.biz.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.dc.api.dto.QueryPageDTO;
import com.pig4cloud.pig.dc.api.entity.OscBanner;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * <p>
 * 轮播图 服务类
 * </p>
 *
 * @author chenlei
 * @since 2021-11-23
 */
public interface IOscBannerService extends IService<OscBanner> {

	Page<OscBanner> page(@RequestBody @Valid QueryPageDTO dto) ;
}
