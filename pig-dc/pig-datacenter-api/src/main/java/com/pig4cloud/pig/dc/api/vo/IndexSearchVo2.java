package com.pig4cloud.pig.dc.api.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 专业
 * </p>
 *
 * @author chenlei
 * @since 2021-11-28
 */
@Data
@ApiModel(value="小程序全局搜索vo对象2", description="小程序全局搜索vo对象2")
public class IndexSearchVo2 implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty( value = "类型 0:全部 1:offer 2:新闻 3:学校 4:专业" , required = true)
	private Integer type;

	@ApiModelProperty( value = "封面图片地址" , required = true)
	private String imageUrl;

	@ApiModelProperty( value = "标题" , required = true)
	private String title;

	@ApiModelProperty( value = "内容1" , required = true)
	private String content1;

	@ApiModelProperty( value = "内容2" , required = true)
	private String content2;

	@ApiModelProperty( value = "内容3" , required = true)
	private String content3;

	@ApiModelProperty( value = "内容4" , required = true)
	private String content4;

	@ApiModelProperty( value = "内容5" , required = true)
	private String content5;



}
