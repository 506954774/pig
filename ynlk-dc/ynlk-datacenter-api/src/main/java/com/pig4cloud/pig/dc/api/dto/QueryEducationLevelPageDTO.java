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
@ApiModel(value="查询留学阶段分页接口,入参父类", description="查询留学阶段分页接口,入参父类")
public class QueryEducationLevelPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;


	@ApiModelProperty( value = "类型" , required = true)
	private Integer educationType;

    @ApiModelProperty( value = "关键字" , required = true)
    private String keyword;

    @ApiModelProperty( value = "每页数量" , required = true)
    private long size=10L;

    @ApiModelProperty( value = "页码" , required = true)
    private long current=1L;


}
