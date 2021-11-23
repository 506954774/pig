package com.pig4cloud.pig.dc.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 轮播图
 * </p>
 *
 * @author chenlei
 * @since 2021-11-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="OscBanner对象", description="轮播图")
public class OscBanner extends Model<OscBanner> {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty( value = "主键" , required = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty( value = "标题" , required = true)
    private String title;

    @ApiModelProperty( value = "图片预览" , required = true)
    private String imageUrl;

    @ApiModelProperty( value = "跳转地址" , required = true)
    private String linkUrl;

    @ApiModelProperty( value = "排序,大的在前面" , required = true)
    private Integer bannerSort;

    @ApiModelProperty( value = "状态,0正常 1冻结" , required = true)
    private String lockFlag;

    @ApiModelProperty( value = "删除标识,0未删除 1已删除" , required = true)
    private String delFlag;

    @ApiModelProperty( value = "创建时间" , required = true)
    private Date createTime;

    @ApiModelProperty( value = "修改时间" , required = true)
    private Date updateTime;

    @ApiModelProperty( value = "创建人" , required = true)
    private String createBy;


}
