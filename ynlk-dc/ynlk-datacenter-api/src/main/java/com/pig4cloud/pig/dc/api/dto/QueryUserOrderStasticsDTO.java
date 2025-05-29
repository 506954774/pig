package com.pig4cloud.pig.dc.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 供应商表
 * </p>
 *
 * @author chenlei
 * @since 2021-11-13
 */
@Data
@ApiModel(value="统计 ,入参父类", description="统计,入参父类")
public class QueryUserOrderStasticsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

	@NotNull(message = "用户id 不可为空")
	@ApiModelProperty( value = " 用户id " , required = true)
	private Integer userId;


}
