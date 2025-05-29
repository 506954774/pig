package com.pig4cloud.pig.dc.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * GoodsDTO
 * 责任人:  ChenLei
 * 修改人： ChenLei
 * 创建/修改时间: 2021/12/14 10:53
 * Copyright :  版权所有
 **/
@NoArgsConstructor
@Data
public class GoodsDTO {

	private String cover_img;
	private String url;
	private String name;
	private Integer price;
	private Integer price2;
	private Integer price_type;
	private Integer goods_id;
	private String third_party_appid;
}
