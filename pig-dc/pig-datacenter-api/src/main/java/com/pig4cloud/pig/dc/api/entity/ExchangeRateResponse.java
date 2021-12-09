package com.pig4cloud.pig.dc.api.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * HL
 * 责任人:  ChenLei
 * 修改人： ChenLei
 * 创建/修改时间: 2021/12/9 9:51
 * Copyright :  版权所有
 **/
public class ExchangeRateResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String showapi_res_error;
	private String showapi_res_id;
	private Integer showapi_res_code;
	private Integer showapi_fee_num;


	private ShowapiResBodyDTO showapi_res_body;


	public String getShowapi_res_error() {
		return showapi_res_error;
	}

	public void setShowapi_res_error(String showapi_res_error) {
		this.showapi_res_error = showapi_res_error;
	}

	public String getShowapi_res_id() {
		return showapi_res_id;
	}

	public void setShowapi_res_id(String showapi_res_id) {
		this.showapi_res_id = showapi_res_id;
	}

	public Integer getShowapi_res_code() {
		return showapi_res_code;
	}

	public void setShowapi_res_code(Integer showapi_res_code) {
		this.showapi_res_code = showapi_res_code;
	}

	public Integer getShowapi_fee_num() {
		return showapi_fee_num;
	}

	public void setShowapi_fee_num(Integer showapi_fee_num) {
		this.showapi_fee_num = showapi_fee_num;
	}

	public ShowapiResBodyDTO getShowapi_res_body() {
		return showapi_res_body;
	}

	public void setShowapi_res_body(ShowapiResBodyDTO showapi_res_body) {
		this.showapi_res_body = showapi_res_body;
	}

	@Override
	public String toString() {
		return "ExchangeRateResponse{" +
				"showapi_res_error='" + showapi_res_error + '\'' +
				", showapi_res_id='" + showapi_res_id + '\'' +
				", showapi_res_code=" + showapi_res_code +
				", showapi_fee_num=" + showapi_fee_num +
				", showapi_res_body=" + showapi_res_body +
				'}';
	}
}
