package com.pig4cloud.pig.dc.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * WebcastRoomResponse
 * 责任人:  ChenLei
 * 修改人： ChenLei
 * 创建/修改时间: 2021/12/14 9:52
 * Copyright :  版权所有
 **/
@NoArgsConstructor
@Data
public class PlaybackResponse implements Serializable {

	private Integer errcode;
	private String errmsg;
	private Integer total;
	private List<PlaybackDTO> live_replay;
}
