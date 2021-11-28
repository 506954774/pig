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
 * 大学
 * </p>
 *
 * @author chenlei
 * @since 2021-11-28
 */
@Data
@ApiModel(value="OscUniversity对象", description="大学")
public class OscUniversity extends Model<OscUniversity> {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty( value = "主键" , required = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty( value = "名称,中文" , required = true)
    private String universityName;

    @ApiModelProperty( value = "名称,英文" , required = true)
    private String universityNameEng;

    @ApiModelProperty( value = "地区id" , required = true)
    private Integer areaId;

    @ApiModelProperty( value = "logo图地址" , required = true)
    private String logoUrl;

    @ApiModelProperty( value = "封面大图地址" , required = true)
    private String imageUrl;

    @ApiModelProperty( value = "国家名称" , required = true)
    private String countryName;

    @ApiModelProperty( value = "省-直辖市名称" , required = true)
    private String provinceName;

    @ApiModelProperty( value = "市区名称" , required = true)
    private String cityName;

    @ApiModelProperty( value = "qs排名" , required = true)
    private Integer qsRank;

    @ApiModelProperty( value = "内容介绍,富文本" , required = true)
    private String content;

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
