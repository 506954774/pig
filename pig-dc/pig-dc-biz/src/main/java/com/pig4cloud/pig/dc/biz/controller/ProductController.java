package com.pig4cloud.pig.dc.biz.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.dc.api.dto.AddOfferDTO;
import com.pig4cloud.pig.dc.api.dto.AddProductDTO;
import com.pig4cloud.pig.dc.api.dto.QueryPageDTO;
import com.pig4cloud.pig.dc.api.entity.OscEnrollInfo;
import com.pig4cloud.pig.dc.api.entity.OscProduct;
import com.pig4cloud.pig.dc.biz.service.IOscProductService;
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
@RequestMapping("/product")
@Api(value = "ProductController", tags = "产品管理")
public class ProductController {

	private final IOscProductService iOscProductService;


	/**
	 * 新增产品信息
	 * @param dto
	 * @return
	 */
	@ApiOperation(value = "新增产品信息 ", notes = "新增产品信息 ")
	@PostMapping("/add")
	public R save(  @RequestBody @Valid AddProductDTO dto) {
		OscProduct entity= BeanUtil.copyProperties(dto,OscProduct.class);
		entity.setCreateTime(new Date());
		entity.setCreateBy(SecurityUtils.getUser().getId()+"");
		return R.ok(iOscProductService.save(entity ));
	}


	/**
	 * 通过id删除产品信息
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除产品信息", notes = "通过id删除产品信息")
	@DeleteMapping("/{id}" )
	public R removeById(@PathVariable Integer id) {
		return R.ok(iOscProductService.getBaseMapper().deleteById(id));
	}


	/**
	 * 修改产品信息
	 * @param dto
	 * @return
	 */
	@ApiOperation(value = "修改产品信息 ", notes = "修改产品信息 ")
	@PostMapping("/update")
	public R update( @RequestBody @Valid OscProduct dto) {
		iOscProductService.updateById(dto);
		return R.ok(null);
	}



	/**
	 *  产品信息分页列表
	 *
	 * @return
	 */
	@ApiOperation(value = " 产品信息分页列表", notes = " 产品信息分页列表")
	@PostMapping("/page")
	public R page(@RequestBody @Valid QueryPageDTO dto) {
		if(dto==null){
			dto=new QueryPageDTO();
		}
		Page page=new Page();
		page.setCurrent(dto.getCurrent());
		page.setSize(dto.getSize());
		final QueryPageDTO finalDto = dto;
		Page page1 = iOscProductService.getBaseMapper().selectPage(
				page, Wrappers.<OscProduct>query().lambda()
						.and(!TextUtils.isEmpty(dto.getKeyword()),wrapper -> wrapper
								. like(OscProduct::getProductName, finalDto.getKeyword())
								.or(). like(OscProduct::getProductSubName, finalDto.getKeyword()))
						//.like(!TextUtils.isEmpty(dto.getKeyword()),OscProduct::getProductName, dto.getKeyword())
				.orderByDesc(OscProduct::getProductPrice)

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
		return R.ok(iOscProductService.getBaseMapper().selectById(id));
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
