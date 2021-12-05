package com.pig4cloud.pig.dc.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.dc.api.dto.AddEnrollDTO;
import com.pig4cloud.pig.dc.api.entity.OscEnrollInfo;

/**
 * <p>
 * 申请 服务类
 * </p>
 *
 * @author chenlei
 * @since 2021-11-23
 */
public interface IOscEnrollInfoService extends IService<OscEnrollInfo> {

	/**
	 * @Name:
	 * @Description: 新增留言
	 * @Param:
	 * @return:
	 * @Author: LeiChen
	 * @Date:2021/12/4 19:12
	 *
	 * */
    Integer saveInfo(AddEnrollDTO dto);
}
