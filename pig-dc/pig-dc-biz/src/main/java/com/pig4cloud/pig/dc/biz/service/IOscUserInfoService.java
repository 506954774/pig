package com.pig4cloud.pig.dc.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.dc.api.entity.OscUserInfo;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author chenlei
 * @since 2021-11-26
 */
public interface IOscUserInfoService extends IService<OscUserInfo> {

	/**
	 * @Name:
	 * @Description: 查询用户信息
	 * @Param:
	 * @return:
	 * @Author: LeiChen
	 * @Date:2021/12/4 15:17
	 *
	 * */
    OscUserInfo details(Integer id);
}
