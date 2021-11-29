package com.pig4cloud.pig.dc.api.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 专业
 * </p>
 *
 * @author chenlei
 * @since 2021-11-28
 */
@Data
@ApiModel(value="专业详情,vo对象", description="专业详情,vo对象")
public class OscMajorDetailsVo extends OscMajorVo{

    private static final long serialVersionUID = 1L;



	@ApiModelProperty( value = "专业内容介绍,富文本" , required = true)
	private String majorDescContent;

	@ApiModelProperty( value = "专业要求,富文本" , required = true)
	private String majorRequirementContent;

	@ApiModelProperty( value = "会员专享,富文本" , required = true)
	private String majorMemberContent;

	@ApiModelProperty( value = "非会员,富文本" , required = true)
	private String notMemberContent;


	@ApiModelProperty( value = "封面大图地址,多个则逗号隔开" , required = true)
	private String universityImageUrl;

	@ApiModelProperty( value = "大学,富文本内容介绍 " , required = true)
	private String universityDescContent;












}
