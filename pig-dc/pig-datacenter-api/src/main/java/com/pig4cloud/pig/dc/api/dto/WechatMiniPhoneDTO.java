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
@ApiModel(value="微信获取手机号,参数2", description="微信获取手机号,参数2")
public class WechatMiniPhoneDTO implements Serializable {

    private static final long serialVersionUID = 1L;

	@NotNull(message = "openId不可为空")
    @ApiModelProperty( value = "openId" , required = true)
    private String openId;

	@NotNull(message = "sessionKey不可为空")
	@ApiModelProperty( value = "sessionKey" , required = true)
	private String sessionKey;

	@NotNull(message = "iv不可为空")
	@ApiModelProperty( value = "iv" , required = true)
	private String iv;

	@NotNull(message = "encryptedData不可为空")
	@ApiModelProperty( value = "encryptedData" , required = true)
	private String encryptedData;

}
