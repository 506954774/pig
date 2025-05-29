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

	@NotNull(message = "类型不可为空")
	@ApiModelProperty( value = "类型" , required = true)
	private Integer educationType;

	@NotNull(message = "标题不可为空")
	@NotEmpty(message = "标题不可为空")
	@ApiModelProperty( value = "标题" , required = true)
	private String title;

	@ApiModelProperty( value = "缩略图" , required = true)
	private String iocnUrl;

	@ApiModelProperty( value = "新闻地址" , required = true)
	private String webUrl;

	@ApiModelProperty( value = " 富文本" , required = true)
	private String content;

	@ApiModelProperty( value = "排序,大的在前面" , required = true)
	private Integer educationLevelSort;


}
