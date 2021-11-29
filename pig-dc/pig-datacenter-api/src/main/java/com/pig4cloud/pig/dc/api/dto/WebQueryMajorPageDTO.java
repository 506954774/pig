package com.pig4cloud.pig.dc.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
@ApiModel(value="查询专业列表分页接口,入参父类", description="查询专业列表分页接口,入参父类")
public class WebQueryMajorPageDTO implements Serializable {

    private static final long serialVersionUID = 1L;


	@ApiModelProperty( value = "学校id" , required = true)
	private Integer universityId;

	@ApiModelProperty( value = "学院id" , required = true)
	private Integer collegeId;

	@ApiModelProperty( value = "学历等级 0副学士 1本科 2硕士 3博士" , required = true)
	private Integer educationLevel;

	@ApiModelProperty( value = "类型" , required = true)
	private String majorType;

    @ApiModelProperty( value = "关键字" , required = true)
    private String keyword;

    @ApiModelProperty( value = "每页数量" , required = true)
    private long size=10L;

    @ApiModelProperty( value = "页码" , required = true)
    private long current=1L;



}
