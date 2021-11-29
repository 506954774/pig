package com.pig4cloud.pig.dc.api.vo;

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
 * 专业
 * </p>
 *
 * @author chenlei
 * @since 2021-11-28
 */
@Data
@ApiModel(value="OscMajorVo对象", description="OscMajorVo对象")
public class OscMajorVo extends Model<OscMajorVo> {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty( value = "主键" , required = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty( value = "名称,中文" , required = true)
    private String majorName;

    @ApiModelProperty( value = "名称,英文" , required = true)
    private String majorNameEng;

	@ApiModelProperty( value = "学历等级 0副学士 1本科 2硕士 3博士" , required = true)
	private Integer educationLevel;

	@ApiModelProperty( value = "类型" , required = true)
	private String majorType;





    @ApiModelProperty( value = "学校中文名" , required = true)
    private String universityName;

	@ApiModelProperty( value = "学校英文名" , required = true)
	private String universityNameEng;


	@ApiModelProperty( value = "学校logo图地址" , required = true)
	private String logoUrl;

	@ApiModelProperty( value = "学校qs排名" , required = true)
	private Integer qsRank;

	@ApiModelProperty( value = "国家名称" , required = true)
	private String countryName;

	@ApiModelProperty( value = "省-直辖市名称" , required = true)
	private String provinceName;

	@ApiModelProperty( value = "市区名称" , required = true)
	private String cityName;





	@ApiModelProperty( value = "学院中文名" , required = true)
	private String collegeName;

	@ApiModelProperty( value = "学院英文名" , required = true)
	private String collegeNameEng;






}
