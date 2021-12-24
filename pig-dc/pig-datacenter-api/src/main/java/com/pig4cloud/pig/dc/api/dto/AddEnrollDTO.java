package com.pig4cloud.pig.dc.api.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 地区
 * </p>
 *
 * @author chenlei
 * @since 2021-11-28
 */
@Data
@ApiModel(value="新增留言,参数对象", description="新增留言,参数对象")
public class AddEnrollDTO extends Model<AddEnrollDTO> {

    private static final long serialVersionUID = 1L;





	@ApiModelProperty( value = "申请的课程（二选一） 0:本科 1:硕士 2:博士" , required = true)
	private String applyType;

	//@NotNull(message = "高中名称不能为空")
	//@NotEmpty(message = "高中名称不能为空")
	@ApiModelProperty( value = "高中名称" , required = true)
	private String highSchoolName;

	//@NotNull(message = "高中英文分数不能为空")
	//@NotEmpty(message = "高中英文分数不能为空")
	@ApiModelProperty( value = "高中英文分数" , required = true)
	private String highSchoolEnglishScore;

	//@NotNull(message = "高中考试分数不能为空")
	//@NotEmpty(message = "高中考试分数不能为空")
	@ApiModelProperty( value = "高中考试分数" , required = true)
	private String highSchoolTotalScore;

	//@NotNull(message = "高中分科情况不能为空")
	@ApiModelProperty( value = "高中分科情况（必选）0:文科 1:理科 2:无分科" , required = true)
	private Integer subjectType;




	@ApiModelProperty( value = "本科就读院校 （必填）" , required = true)
	private String universityName;

	@ApiModelProperty( value = "所在的年级 （必选）" , required = true)
	private String universityGrade;

	@ApiModelProperty( value = "想读的课程？（必选） 0:授课型 1:研究型 2:博士 3:暂无" , required = true)
	private String universitySubjectIntent;


	@NotNull(message = "报名者名称不能为空")
	@NotEmpty(message = "报名者名称不能为空")
	@ApiModelProperty( value = "报名者名称" , required = true)
	private String userName;


	@NotNull(message = "报名者电话号码不能为空")
	@NotEmpty(message = "报名者电话号码不能为空")
	@ApiModelProperty( value = "报名者电话号码" , required = true)
	private String userTel;

	@NotNull(message = "微信号码不能为空")
	@NotEmpty(message = "微信号码不能为空")
	@ApiModelProperty( value = "报名者微信号码" , required = true)
	private String userWechatAccount;


	@ApiModelProperty( value = "您目前所学专业及大学均分（GPA） （必填）" , required = true)
	private String universityGpa;

	@ApiModelProperty( value = "您目前是否有语言成绩 CECT分数 （必填）" , required = true)
	private String universityCectScore;

	@ApiModelProperty( value = "硕士就读院校 （必填）" , required = true)
	private String masterSchoolName;

	@ApiModelProperty( value = "您目前所在的年级（单选）" , required = true)
	private String masterGrade;

	@ApiModelProperty( value = "您目前所学专业及大学均分（GPA）" , required = true)
	private String masterGpa;

	@ApiModelProperty( value = "您目前是否有语言成绩 CECT" , required = true)
	private String masterCectScore;
}
