package com.pig4cloud.pig.dc.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 留学,学历阶段
 * </p>
 *
 * @author chenlei
 * @since 2021-12-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="OscEducationLevel对象", description="留学,学历阶段")
public class OscEducationLevel extends Model<OscEducationLevel> {

    private static final long serialVersionUID = 1L;



	@ApiModelProperty( value = "主键" , required = true)
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty( value = "类型" , required = true)
	private Integer educationType;

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

	@ApiModelProperty( value = "状态,0正常 1冻结" , required = true)
	private String lockFlag;

	@TableLogic
	@ApiModelProperty( value = "删除标识,0未删除 1已删除" , required = true)
	private String delFlag;

	@ApiModelProperty( value = "创建时间" , required = true)
	private Date createTime;

	@ApiModelProperty( value = "修改时间" , required = true)
	private Date updateTime;

	@ApiModelProperty( value = "创建人" , required = true)
	private String createBy;


}
