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
@ApiModel(value="微信code获取openId,参数2", description="微信code获取openId,参数2")
public class WechatMiniCode2SessionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

	@NotNull(message = "code不可为空")
    @ApiModelProperty( value = "code" , required = true)
    private String code;



}
