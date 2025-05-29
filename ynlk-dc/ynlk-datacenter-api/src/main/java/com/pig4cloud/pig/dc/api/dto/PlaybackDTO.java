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
public class PlaybackDTO {

	private String expire_time;
	private String create_time;
	private String media_url;

}
