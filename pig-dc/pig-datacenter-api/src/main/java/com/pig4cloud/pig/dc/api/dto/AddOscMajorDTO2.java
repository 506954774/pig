package com.pig4cloud.pig.dc.api.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 专业
 * </p>
 *
 * @author chenlei
 * @since 2021-11-28
 */
@Data
@ApiModel(value="新增OscMajor对象2", description="新增OscMajor对象2")
public class AddOscMajorDTO2 extends Model<AddOscMajorDTO2> {

    private static final long serialVersionUID = 1L;



    @NotNull(message = "名称不可为空")
    @NotEmpty(message = "名称不可为空")
    @ApiModelProperty( value = "名称,中文" , required = true)
    private String majorName;

    @ApiModelProperty( value = "名称,英文" , required = true)
    private String majorNameEng;

	@NotNull(message = "学校名称不可为空")
    @ApiModelProperty( value = "学校名称" , required = true)
    private String universityName;

	@NotNull(message = "学院名称不可为空")
    @ApiModelProperty( value = "学院名称" , required = true)
    private String collegeName;


	@NotNull(message = "专业类型不可为空")
	@ApiModelProperty( value = "类型" , required = true)
	private String majorType;

	@NotNull(message = "学历等级不可为空")
    @ApiModelProperty( value = "学历等级 0副学士 1本科 2硕士 3博士" , required = true)
    private Integer educationLevel;

    @ApiModelProperty( value = "内容介绍,富文本" , required = true)
    private String descContent;

    @ApiModelProperty( value = "专业要求,富文本" , required = true)
    private String requirementContent;

    @ApiModelProperty( value = "会员专享,富文本" , required = true)
    private String memberContent;

	@ApiModelProperty( value = "非会员,富文本" , required = true)
	private String notMemberContent;



}
