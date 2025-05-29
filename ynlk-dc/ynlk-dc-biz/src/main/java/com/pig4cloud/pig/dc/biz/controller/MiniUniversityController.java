package com.pig4cloud.pig.dc.biz.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.dc.api.dto.AddOscCollegeDTO;
import com.pig4cloud.pig.dc.api.dto.AddOscUniversityDTO;
import com.pig4cloud.pig.dc.api.dto.QueryCollegePageDTO;
import com.pig4cloud.pig.dc.api.dto.QueryUniversityPageDTO;
import com.pig4cloud.pig.dc.api.entity.OscUniversity;
import com.pig4cloud.pig.dc.biz.service.IOscUniversityService;
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
@RequestMapping("/mini/university")
@Api(value = "MiniUniversityController", tags = "小程序-学校模块")
public class MiniUniversityController {

	private final IOscUniversityService iOscUniversityService;




	/**
	 *  大学分页列表
	 *
	 * @return
	 */
	@ApiOperation(value = " 大学分页列表", notes = " 大学分页列表")
	@PostMapping("/page")
	public R page(@RequestBody @Valid QueryUniversityPageDTO dto) {
		if(dto==null){
			dto=new QueryUniversityPageDTO();
		}
		Page page=new Page();
		page.setCurrent(dto.getCurrent());
		page.setSize(dto.getSize());
		final QueryUniversityPageDTO finalDto = dto;
		Page page1 = iOscUniversityService.getBaseMapper().selectPage(
				page, Wrappers.<OscUniversity>query().lambda()
						.eq(dto.getAreaId()!=null,OscUniversity::getAreaId,dto.getAreaId())
						.and(!TextUtils.isEmpty(dto.getAreaName()),

								wrapper1->wrapper1.like(OscUniversity::getUniversityName, finalDto.getAreaName())
										.or(
												wrapper -> wrapper
														. like(OscUniversity::getCityName, finalDto.getAreaName())
														.or(). like(OscUniversity::getCountryName, finalDto.getAreaName()
														)
										)
						)
						.and(!TextUtils.isEmpty(dto.getKeyword()),

								wrapper1->wrapper1.like(OscUniversity::getUniversityName, finalDto.getKeyword())
								.or(

								wrapper -> wrapper
								. like(OscUniversity::getUniversityNameEng, finalDto.getKeyword())
								.or(). like(OscUniversity::getCountryName, finalDto.getKeyword()
										)
								)
						)
						//.like(!TextUtils.isEmpty(dto.getKeyword()),OscUniversity::getStuName, dto.getKeyword())
				//.orderByDesc(OscUniversity::getQsRank)
				.orderByDesc(OscUniversity::getUniversitySort)

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
		return R.ok(iOscUniversityService.details(id));
	}


	/**
	 * 根据大学id查询全部学院列表
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "根据大学id查询全部学院列表", notes = "根据大学id查询全部学院列表")
	@GetMapping("/colleges/{id}" )
	public R queryCollegesByUniversityId(@PathVariable Integer id) {
		return R.ok(iOscUniversityService.queryCollegesByUniversityId(id));
	}


	/**
	 * 根据大学id查询全部学院分页列表
	 * @param dto 参数
	 * @return R
	 */
	@ApiOperation(value = "根据大学id查询全部学院列表", notes = "根据大学id查询全部学院列表")
	@PostMapping("/colleges/page" )
	public R queryCollegesPageByUniversityId( @RequestBody @Valid QueryCollegePageDTO dto) {
		return R.ok(iOscUniversityService.queryCollegesPageByUniversityId(dto));
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
