package com.pig4cloud.pig.dc.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 供应商表
 * </p>
 *
 * @author chenlei
 * @since 2021-11-13
 */
@Data
@ApiModel(value="微信小程序,购买会员,参数3", description="微信小程序,购买会员,参数3")
public class WechatMiniPayMemberDTO implements Serializable {

    private static final long serialVersionUID = 1L;

	@NotNull(message = "openId不可为空")
    @ApiModelProperty( value = "openId" , required = true)
    private String openId;

}
