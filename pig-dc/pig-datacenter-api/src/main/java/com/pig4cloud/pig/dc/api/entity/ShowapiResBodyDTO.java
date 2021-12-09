package com.pig4cloud.pig.dc.api.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * ShowapiResBodyDTO
 * 责任人:  ChenLei
 * 修改人： ChenLei
 * 创建/修改时间: 2021/12/9 9:51
 * Copyright :  版权所有
 **/

public class ShowapiResBodyDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer ret_code;
	private String money;

	public Integer getRet_code() {
		return ret_code;
	}

	public void setRet_code(Integer ret_code) {
		this.ret_code = ret_code;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	@Override
	public String toString() {
		return "ShowapiResBodyDTO{" +
				"ret_code=" + ret_code +
				", money='" + money + '\'' +
				'}';
	}
}
