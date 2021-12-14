package com.pig4cloud.pig.dc.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * RoomInfoDTO
 * 责任人:  ChenLei
 * 修改人： ChenLei
 * 创建/修改时间: 2021/12/14 10:53
 * Copyright :  版权所有
 **/
@NoArgsConstructor
@Data
public class RoomInfoDTO {

	private String name;
	private Integer roomid;
	private String cover_img;
	private String share_img;
	private Integer live_status;
	private Integer start_time;
	private Integer end_time;
	private String anchor_name;
	private List<GoodsDTO> goods;
	private Integer live_type;
	private Integer close_like;
	private Integer close_goods;
	private Integer close_comment;
	private Integer close_kf;
	private Integer close_replay;
	private Integer is_feeds_public;
	private String creater_openid;
	private String feeds_img;
}
