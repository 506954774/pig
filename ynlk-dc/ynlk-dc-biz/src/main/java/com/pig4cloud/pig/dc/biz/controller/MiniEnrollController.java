package com.pig4cloud.pig.dc.biz.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.dc.api.dto.AddEnrollDTO;
import com.pig4cloud.pig.dc.api.dto.QueryUserMessagePageDTO;
import com.pig4cloud.pig.dc.api.entity.OscBanner;
import com.pig4cloud.pig.dc.api.entity.OscEnrollInfo;
import com.pig4cloud.pig.dc.biz.service.IOscEnrollInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.http.util.TextUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**
 * @author LeiChen
 * @version 1.0
 * @date 2021/5/21 12:37
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mini/message")
@Api(value = "MiniEnrollController", tags = "小程序-留言")
public class MiniEnrollController {

	private final IOscEnrollInfoService iOscBannerService;

	/**
	 * 新增留言
	 * @param dto
	 * @return
	 */
	@ApiOperation(value = "新增留言 ", notes = "新增留言 ")
	@PostMapping("/add")
	public R save(  @RequestBody @Valid AddEnrollDTO dto) {
		return R.ok(iOscBannerService.saveInfo(dto ));
	}


	/**
	 *  用户查询自己的留言列表
	 *
	 * @return
	 */
	@ApiOperation(value = " 用户查询自己的留言列表", notes = " 用户查询自己的留言列表")
	@PostMapping("/page")
	public R page(@RequestBody @Valid QueryUserMessagePageDTO dto) {
		if(dto==null){
			dto=new QueryUserMessagePageDTO();
		}
		Page page=new Page();
		page.setCurrent(dto.getCurrent());
		page.setSize(dto.getSize());
		final QueryUserMessagePageDTO finalDto = dto;

		Page page1 = iOscBannerService.getBaseMapper().selectPage(
				page, Wrappers.<OscEnrollInfo>query().lambda()
						.eq(OscEnrollInfo::getUserId,dto.getUserId())
						.and(!TextUtils.isEmpty(dto.getKeyword()),wrapper -> wrapper
								. like(OscEnrollInfo::getUserName, finalDto.getKeyword())
								.or(). like(OscEnrollInfo::getMajorName, finalDto.getKeyword()))
						.like(!TextUtils.isEmpty(dto.getKeyword()),OscEnrollInfo::getUserName, dto.getKeyword())
				.orderByDesc(OscEnrollInfo::getCreateTime)

		);
		return R.ok(page1);
	}


	/**
	 * 根据id查询详情
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "根据id查询详情", notes = "根据id查询详情")
	@GetMapping("/details/{id}" )
	public R details(@PathVariable Integer id) {
		return R.ok(iOscBannerService.getBaseMapper().selectById(id));
	}


	/*
	*//**
	 *  获取计划类型列表
	 *
	 * @return
	 *//*
	@ApiOperation(response = PmPlanType.class, value = " 获取计划类型列表", notes = " 获取计划类型列表")
	@GetMapping("/list/page")
	public R listPage(@RequestHeader(name = "TENANT-ID") Integer tenantId , @RequestHeader(name = "Project-Id") Integer projectId  , Page page ) {
		System.out.println("tenantId====================="+TenantContextHolder.getTenantId());
		return R.ok(iPmPlanTypeService.page(page ));
	}*/



}
