package com.pig4cloud.pig.dc.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 专业
 * </p>
 *
 * @author chenlei
 * @since 2021-11-28
 */
@Data
@ApiModel(value="OscMajor对象", description="专业")
public class OscMajor extends Model<OscMajor> {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty( value = "主键" , required = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty( value = "名称,中文" , required = true)
    private String majorName;

    @ApiModelProperty( value = "名称,英文" , required = true)
    private String majorNameEng;


	@ApiModelProperty( value = "类型" , required = true)
	private String majorType;

    @ApiModelProperty( value = "学校id" , required = true)
    private Integer universityId;

    @ApiModelProperty( value = "学院id" , required = true)
    private Integer collegeId;

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

    @ApiModelProperty( value = "是否首页推荐,1,是" , required = true)
    private Integer recommendFlag;

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
