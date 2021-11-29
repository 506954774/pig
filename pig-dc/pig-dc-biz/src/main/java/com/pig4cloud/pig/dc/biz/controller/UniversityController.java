package com.pig4cloud.pig.dc.biz.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.dc.api.dto.*;
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
@RequestMapping("/university")
@Api(value = "UniversityController", tags = "大学管理")
public class UniversityController {

	private final IOscUniversityService iOscUniversityService;


	/**
	 * 新增大学
	 * @param dto
	 * @return
	 */
	@ApiOperation(value = "新增大学 ", notes = "新增大学 ")
	@PostMapping("/add")
	public R save(  @RequestBody @Valid AddOscUniversityDTO dto) {
		OscUniversity entity= BeanUtil.copyProperties(dto,OscUniversity.class);
		entity.setCreateTime(new Date());
		entity.setCreateBy(SecurityUtils.getUser().getId()+"");
		return R.ok(iOscUniversityService.save(entity ));
	}


	/**
	 * 通过id删除大学
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除大学", notes = "通过id删除大学")
	@DeleteMapping("/{id}" )
	public R removeById(@PathVariable Integer id) {
		return R.ok(iOscUniversityService.getBaseMapper().deleteById(id));
	}


	/**
	 * 修改大学
	 * @param dto
	 * @return
	 */
	@ApiOperation(value = "修改大学 ", notes = "修改大学 ")
	@PostMapping("/update")
	public R update( @RequestBody @Valid OscUniversity dto) {
		iOscUniversityService.updateById(dto);
		return R.ok(null);
	}



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
				.orderByDesc(OscUniversity::getQsRank)

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


	/**
	 * 给大学新增学院
	 * @param dto
	 * @return
	 */
	@ApiOperation(value = "给大学新增学院 ", notes = "给大学新增学院 ")
	@PostMapping("/colleges/add")
	public R update( @RequestBody @Valid AddOscCollegeDTO dto) {
		iOscUniversityService.insertCollege(dto);
		return R.ok(null);
	}



	/**
	 * 通过id删除学院
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除学院", notes = "通过id删除学院")
	@DeleteMapping("/college/{id}" )
	public R deleteCollegeById(@PathVariable Integer id) {
		return R.ok(iOscUniversityService. deleteCollegeById(id));
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
