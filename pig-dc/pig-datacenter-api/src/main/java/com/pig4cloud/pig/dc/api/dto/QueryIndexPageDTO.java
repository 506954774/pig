package com.pig4cloud.pig.dc.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 供应商表
 * </p>
 *
 * @author chenlei
 * @since 2021-11-13
 */
@Data
@ApiModel(value="小程序搜索分页接口,入参父类", description="小程序搜索分页接口,入参父类")
public class QueryIndexPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

	@ApiModelProperty( value = "类型 0:全部 1:offer 2:新闻 3:学校 4:专业" , required = true)
	private Integer type;

    @ApiModelProperty( value = "关键字" , required = true)
    private String keyword;

    @ApiModelProperty( value = "每页数量" , required = true)
    private long size=10L;

    @ApiModelProperty( value = "页码" , required = true)
    private long current=1L;



}
