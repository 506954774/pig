package com.pig4cloud.pig.dc.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * WechatLoginDto
 * 责任人:  ChenLei
 * 修改人： ChenLei
 * 创建/修改时间: 2021/11/8 23:42
 * Copyright :  版权所有
 **/
@Data
@ApiModel(value = "微信登录,qo")
public class WechatLoginDto implements Serializable {


	@ApiModelProperty(value = "unionid,必填")
	private String unionid;


	@ApiModelProperty(value = "昵称,选填")
	private String nickname;

	/**
	 * 手机号
	 */
	@ApiModelProperty(value = "手机号,选填")
	private String phone;

	/**
	 * 头像
	 */
	@ApiModelProperty(value = "头像地址,选填")
	private String avatar;
}
