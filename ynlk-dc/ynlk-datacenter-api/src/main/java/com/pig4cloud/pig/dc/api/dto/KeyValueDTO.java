package com.pig4cloud.pig.dc.api.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 键值对
 * </p>
 *
 * @author chenlei
 * @since 2021-11-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="键值对", description="键值对")
public class KeyValueDTO extends Model<KeyValueDTO> {

    private static final long serialVersionUID = 1L;


	@NotNull(message = "键不可为空")
	@NotEmpty(message = "键不可为空")
	@ApiModelProperty( value = "键" , required = true)
	private String key;

	@NotNull(message = "值不可为空")
	@NotEmpty(message = "值不可为空")
	@ApiModelProperty( value = "值" , required = true)
	private String value;




}
