package com.pig4cloud.pig.dc.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
@ApiModel(value="用户发布的留言分页接口,入参父类", description="用户发布的留言分页接口,入参父类")
public class QueryUserMessagePageDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @NotNull(message ="用户id不可为空")
	@ApiModelProperty( value = "报名者id" , required = true)
	private Integer userId;

    @ApiModelProperty( value = "关键字" , required = true)
    private String keyword;

    @ApiModelProperty( value = "每页数量" , required = true)
    private long size=10L;

    @ApiModelProperty( value = "页码" , required = true)
    private long current=1L;



}
