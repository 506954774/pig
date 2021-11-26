package com.pig4cloud.pig.dc.biz.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.dc.api.dto.AddIndexGuideDTO;
import com.pig4cloud.pig.dc.api.dto.QueryPageDTO;
import com.pig4cloud.pig.dc.api.entity.OscEnrollInfo;
import com.pig4cloud.pig.dc.api.entity.OscIndexGuide;
import com.pig4cloud.pig.dc.biz.service.IOscIndexGuideService;
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
@RequestMapping("/guide")
@Api(value = "IndexGuideController", tags = "首页导航管理")
public class IndexGuideController {

	private final IOscIndexGuideService iOscIndexGuideService;


	/**
	 * 新增首页导航
	 * @param dto
	 * @return
	 */
	@ApiOperation(value = "新增首页导航 ", notes = "新增首页导航 ")
	@PostMapping("/add")
	public R save(  @RequestBody @Valid AddIndexGuideDTO dto) {
		OscIndexGuide entity= BeanUtil.copyProperties(dto,OscIndexGuide.class);
		entity.setCreateTime(new Date());
		entity.setCreateBy(SecurityUtils.getUser().getId()+"");
		return R.ok(iOscIndexGuideService.save(entity ));
	}


	/**
	 * 通过id删除首页导航
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除首页导航", notes = "通过id删除首页导航")
	@DeleteMapping("/{id}" )
	public R removeById(@PathVariable Integer id) {
		return R.ok(iOscIndexGuideService.getBaseMapper().deleteById(id));
	}


	/**
	 * 修改首页导航
	 * @param dto
	 * @return
	 */
	@ApiOperation(value = "修改首页导航 ", notes = "修改首页导航 ")
	@PostMapping("/update")
	public R update( @RequestBody @Valid OscIndexGuide dto) {
		iOscIndexGuideService.updateById(dto);
		return R.ok(null);
	}



	/**
	 *  首页导航分页列表
	 *
	 * @return
	 */
	@ApiOperation(value = " 首页导航分页列表", notes = " 首页导航分页列表")
	@PostMapping("/page")
	public R page(@RequestBody @Valid QueryPageDTO dto) {
		if(dto==null){
			dto=new QueryPageDTO();
		}
		Page page=new Page();
		page.setCurrent(dto.getCurrent());
		page.setSize(dto.getSize());
		final QueryPageDTO finalDto = dto;
		Page page1 = iOscIndexGuideService.getBaseMapper().selectPage(
				page, Wrappers.<OscIndexGuide>query().lambda()
						.and(!TextUtils.isEmpty(dto.getKeyword()),wrapper -> wrapper
								. like(OscIndexGuide::getTitle, finalDto.getKeyword())
								.or(). like(OscIndexGuide::getContent, finalDto.getKeyword()))
						//.like(!TextUtils.isEmpty(dto.getKeyword()),OscIndexGuide::getTitle, dto.getKeyword())
				.orderByDesc(OscIndexGuide::getIndexSort)

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
		return R.ok(iOscIndexGuideService.getBaseMapper().selectById(id));
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
