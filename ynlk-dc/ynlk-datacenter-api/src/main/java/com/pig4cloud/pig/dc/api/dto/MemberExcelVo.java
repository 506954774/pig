package com.pig4cloud.pig.dc.api.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
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
 * 用户信息表
 * </p>
 *
 * @author chenlei
 * @since 2021-11-26
 */
@Data
@ApiModel(value="MemberExcelVo", description="MemberExcelVo")
public class MemberExcelVo implements Serializable {

    private static final long serialVersionUID = 1L;


	@ColumnWidth(20)
	@ExcelProperty("电话号码")
	@ApiModelProperty( value = "电话号码" , required = true)
	private String phone;


	@ColumnWidth(20)
	@ExcelProperty("注册时间")
	@ApiModelProperty( value = "注册时间" , required = true)
	private String registerTime;


}
