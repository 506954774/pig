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
 * 申请
 * </p>
 *
 * @author chenlei
 * @since 2021-11-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="OscEnrollInfo对象", description="申请")
public class OscEnrollInfo extends Model<OscEnrollInfo> {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty( value = "主键" , required = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty( value = "报名者id" , required = true)
    private Integer userId;

    @ApiModelProperty( value = "报名者名称" , required = true)
    private String userName;

    @ApiModelProperty( value = "报名者电话号码" , required = true)
    private String userTel;

    @ApiModelProperty( value = "报名者微信号码" , required = true)
    private String userWechatAccount;

    @ApiModelProperty( value = "意向的专业id" , required = true)
    private Integer majorId;

    @ApiModelProperty( value = "意向的专业名称" , required = true)
    private String majorName;

    @ApiModelProperty( value = "申请时间" , required = true)
    private Date applyTime;

    @ApiModelProperty( value = "状态,0未处理 1已处理" , required = true)
    private String status;

    @ApiModelProperty( value = "处理人" , required = true)
    private String operatorId;

    @ApiModelProperty( value = "处理的时间" , required = true)
    private Date operateTime;

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

	@ApiModelProperty( value = "高中名称" , required = true)
	private String highSchoolName;

	@ApiModelProperty( value = "高中英文分数" , required = true)
	private String highSchoolEnglishScore;

	@ApiModelProperty( value = "高中考试分数" , required = true)
	private String highSchoolTotalScore;

	@ApiModelProperty( value = "高中分科情况（必选）0:文科 1:理科 2:无分科" , required = true)
	private Integer subjectType;

	@ApiModelProperty( value = "联系人" , required = true)
	private String contact;


}
