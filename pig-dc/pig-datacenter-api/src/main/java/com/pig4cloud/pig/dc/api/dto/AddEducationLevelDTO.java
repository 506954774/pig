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
 * offer榜单
 * </p>
 *
 * @author chenlei
 * @since 2021-11-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="留学阶段,录入参数", description="留学阶段,录入参数")
public class AddEducationLevelDTO extends Model<AddEducationLevelDTO> {

    private static final long serialVersionUID = 1L;

	@NotNull(message = "名称不可为空")
	@NotEmpty(message = "名称不可为空")
	@ApiModelProperty( value = "名称,中文" , required = true)
	private String educationLevelName;

	@ApiModelProperty( value = "名称,英文" , required = true)
	private String educationLevelNameEng;

	@ApiModelProperty( value = "排序,大的在前面" , required = true)
	private Integer educationLevelSort;



}
