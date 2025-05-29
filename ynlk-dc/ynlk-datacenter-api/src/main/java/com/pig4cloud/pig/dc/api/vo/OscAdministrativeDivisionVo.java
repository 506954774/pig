package com.pig4cloud.pig.dc.api.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 行政区
 * </p>
 *
 * @author chenlei
 * @since 2021-11-28
 */
@Data
@ApiModel(value="OscAdministrativeDivision对象", description="行政区")
public class OscAdministrativeDivisionVo extends Model<OscAdministrativeDivisionVo> {

	public OscAdministrativeDivisionVo( ) {
	}

    private static final long serialVersionUID = 1L;

    @ApiModelProperty( value = "主键" , required = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty( value = "名称,中文" , required = true)
    private String areaName;

    @ApiModelProperty( value = "名称,英文" , required = true)
    private String areaNameEng;

    @ApiModelProperty( value = "父id" , required = true)
    private Integer parentId;

	@ApiModelProperty( value = "子节点数量" , required = true)
	private Integer childNodeSize;

	@ApiModelProperty( value = "子节点" , required = true)
	private List<OscAdministrativeDivisionVo> children;

	public OscAdministrativeDivisionVo(Integer id, String areaName, Integer parentId) {
		this.id = id;
		this.areaName = areaName;
		this.parentId = parentId;
	}

}
