package com.pig4cloud.pig.dc.api.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value="小程序全局搜索vo对象", description="小程序全局搜索vo对象")
public class IndexSearchVo extends OscMajorVo {

    private static final long serialVersionUID = 1L;

	@ApiModelProperty( value = "类型 0:全部 1:offer 2:新闻 3:学校 4:专业" , required = true)
	private Integer type;


	@ApiModelProperty( value = "offer-学生姓名" , required = true)
	private String offerStuName;

	@ApiModelProperty( value = "offer-学校信息" , required = true)
	private String offerSchoolDesc;

	@ApiModelProperty( value = "offer-专业名称" , required = true)
	private String offerMajorName;

	@ApiModelProperty( value = "offer-封面图片地址" , required = true)
	private String offerImageUrl;


	@ApiModelProperty( value = "新闻-标题" , required = true)
	private String newsTitle;

	@ApiModelProperty( value = "新闻-新闻地址" , required = true)
	private String newsWebUrl;

	@ApiModelProperty( value = "新闻-缩略图" , required = true)
	private String newsIocnUrl;



}
