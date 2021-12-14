package com.pig4cloud.pig.dc.api.entity;

import java.io.Serializable;

/**
 * WechatAccessTokenResponse
 * 责任人:  ChenLei
 * 修改人： ChenLei
 * 创建/修改时间: 2021/12/13 16:59
 * Copyright :  版权所有
 **/

public class WechatAccessTokenResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String access_token;
	private Integer expires_in;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public Integer getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(Integer expires_in) {
		this.expires_in = expires_in;
	}

	@Override
	public String toString() {
		return "WechatAccessTokenResponse{" +
				"access_token='" + access_token + '\'' +
				", expires_in=" + expires_in +
				'}';
	}
}
