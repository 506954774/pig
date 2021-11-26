package com.pig4cloud.pig.dc.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author chenlei
 * @since 2021-11-26
 */
@Data
@ApiModel(value="OscUserInfo对象", description="用户信息表")
public class OscUserInfo extends Model<OscUserInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(type= IdType.NONE)
    @ApiModelProperty( value = "主键ID" , required = true)
    private Integer userId;

    @ApiModelProperty( value = "昵称" , required = true)
    private String nickname;

    @ApiModelProperty( value = "姓名" , required = true)
    private String realname;

    @ApiModelProperty( value = "头像" , required = true)
    private String avatar;

    @ApiModelProperty( value = "部门ID" , required = true)
    private Integer deptId;

    @ApiModelProperty( value = "创建时间" , required = true)
    private Date createTime;

    @ApiModelProperty( value = "修改时间" , required = true)
    private Date updateTime;

    @ApiModelProperty( value = "0-正常，9-锁定" , required = true)
    private String lockFlag;

    @ApiModelProperty( value = "0-正常，1-删除" , required = true)
    private String delFlag;


}
