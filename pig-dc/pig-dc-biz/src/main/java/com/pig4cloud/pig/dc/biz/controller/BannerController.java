package com.pig4cloud.pig.dc.biz.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.dc.api.dto.AddOscBannerDTO;
import com.pig4cloud.pig.dc.api.dto.QueryPageDTO;
import com.pig4cloud.pig.dc.api.entity.OscBanner;
import com.pig4cloud.pig.dc.biz.service.IOscBannerService;
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
@RequestMapping("/banner")
@Api(value = "BannerController", tags = "轮播图管理")
public class BannerController {

	private final IOscBannerService iOscBannerService;


	/**
	 * 新增轮播图
	 * @param dto
	 * @return
	 */
	@ApiOperation(value = "新增轮播图 ", notes = "新增轮播图 ")
	@PostMapping("/add")
	public R save(  @RequestBody @Valid AddOscBannerDTO dto) {
		OscBanner entity= BeanUtil.copyProperties(dto,OscBanner.class);
		entity.setCreateTime(new Date());
		entity.setCreateBy(SecurityUtils.getUser().getId()+"");
		return R.ok(iOscBannerService.save(entity ));
	}


	/**
	 * 通过id删除轮播图
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除轮播图", notes = "通过id删除轮播图")
	@DeleteMapping("/{id}" )
	public R removeById(@PathVariable Integer id) {
		return R.ok(iOscBannerService.getBaseMapper().deleteById(id));
	}


	/**
	 * 修改轮播图
	 * @param dto
	 * @return
	 */
	@ApiOperation(value = "修改轮播图 ", notes = "修改轮播图 ")
	@PostMapping("/update")
	public R update( @RequestBody @Valid OscBanner dto) {
		iOscBannerService.updateById(dto);
		return R.ok(null);
	}



	/**
	 *  轮播图分页列表
	 *
	 * @return
	 */
	@ApiOperation(value = " 轮播图分页列表", notes = " 轮播图分页列表")
	@PostMapping("/page")
	public R page(@RequestBody @Valid QueryPageDTO dto) {
		if(dto==null){
			dto=new QueryPageDTO();
		}
		Page page=new Page();
		page.setCurrent(dto.getCurrent());
		page.setSize(dto.getSize());
		Page page1 = iOscBannerService.getBaseMapper().selectPage(
				page, Wrappers.<OscBanner>query().lambda()
						.like(!TextUtils.isEmpty(dto.getKeyword()),OscBanner::getTitle, dto.getKeyword())
				.orderByDesc(OscBanner::getBannerSort)

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
