package com.pig4cloud.pig.dc.api.entity;

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

    @TableId(type= IdType.INPUT)
    @ApiModelProperty( value = "主键ID" , required = true)
    private Integer userId;

	@ApiModelProperty( value = "微信openId,也就是umps里面用户的登录账号" , required = true)
	private String openid;

	@ApiModelProperty( value = "电话号码" , required = true)
	private String phone;

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

	@ApiModelProperty( value = "0-正常，1-锁定" , required = true)
	private String lockFlag;

	@ApiModelProperty( value = "是否会员,标识" , required = true)
	private String memberFlag;

	@TableLogic
	@ApiModelProperty( value = "0-正常，1-删除" , required = true)
	private String delFlag;

	@ApiModelProperty( value = "用户的微信号码" , required = true)
	private String userWechatAccount;

}
