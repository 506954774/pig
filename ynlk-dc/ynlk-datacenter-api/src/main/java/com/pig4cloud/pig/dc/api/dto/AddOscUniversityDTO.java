package com.pig4cloud.pig.dc.api.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
@ApiModel(value="新增OscUniversity对象", description="新增OscUniversity对象")
public class AddOscUniversityDTO extends Model<AddOscUniversityDTO> {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty( value = "名称,中文" , required = true)
    private String universityName;

    @ApiModelProperty( value = "名称,英文" , required = true)
    private String universityNameEng;

    @ApiModelProperty( value = "地区id" , required = true)
    private Integer areaId;

    @ApiModelProperty( value = "logo图地址" , required = true)
    private String logoUrl;

    @ApiModelProperty( value = "封面大图地址,多个则逗号隔开" , required = true)
    private String imageUrl;

    @ApiModelProperty( value = "国家名称" , required = true)
    private String countryName;

    @ApiModelProperty( value = "省-直辖市名称" , required = true)
    private String provinceName;

    @ApiModelProperty( value = "市区名称" , required = true)
    private String cityName;

    @ApiModelProperty( value = "qs排名" , required = true)
    private String qsRank;

    @ApiModelProperty( value = "内容介绍,富文本" , required = true)
    private String content;

    @ApiModelProperty( value = "是否首页推荐,1,是" , required = true)
    private Integer recommendFlag;

    @ApiModelProperty( value = "状态,0正常 1冻结" , required = true)
    private String lockFlag;

	@ApiModelProperty( value = "自定义排序" , required = true)
	private Integer universitySort;



}
