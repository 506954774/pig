package com.pig4cloud.pig.dc.biz.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.dc.api.dto.AddOfferDTO;
import com.pig4cloud.pig.dc.api.dto.QueryPageDTO;
import com.pig4cloud.pig.dc.api.entity.OscOffer;
import com.pig4cloud.pig.dc.biz.service.IOscOfferService;
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
@RequestMapping("/offer")
@Api(value = "OffferController", tags = "offer榜单管理")
public class OffferController {

	private final IOscOfferService iOscOfferService;


	/**
	 * 新增offer榜单
	 * @param dto
	 * @return
	 */
	@ApiOperation(value = "新增offer榜单 ", notes = "新增offer榜单 ")
	@PostMapping("/add")
	public R save(  @RequestBody @Valid AddOfferDTO dto) {
		OscOffer entity= BeanUtil.copyProperties(dto,OscOffer.class);
		entity.setCreateTime(new Date());
		entity.setCreateBy(SecurityUtils.getUser().getId()+"");
		return R.ok(iOscOfferService.save(entity ));
	}


	/**
	 * 通过id删除offer榜单
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除offer榜单", notes = "通过id删除offer榜单")
	@DeleteMapping("/{id}" )
	public R removeById(@PathVariable Integer id) {
		return R.ok(iOscOfferService.getBaseMapper().deleteById(id));
	}


	/**
	 * 修改offer榜单
	 * @param dto
	 * @return
	 */
	@ApiOperation(value = "修改offer榜单 ", notes = "修改offer榜单 ")
	@PostMapping("/update")
	public R update( @RequestBody @Valid OscOffer dto) {
		iOscOfferService.updateById(dto);
		return R.ok(null);
	}



	/**
	 *  offer榜单分页列表
	 *
	 * @return
	 */
	@ApiOperation(value = " offer榜单分页列表", notes = " offer榜单分页列表")
	@PostMapping("/page")
	public R page(@RequestBody @Valid QueryPageDTO dto) {
		if(dto==null){
			dto=new QueryPageDTO();
		}
		Page page=new Page();
		page.setCurrent(dto.getCurrent());
		page.setSize(dto.getSize());
		Page page1 = iOscOfferService.getBaseMapper().selectPage(
				page, Wrappers.<OscOffer>query().lambda()
						.like(!TextUtils.isEmpty(dto.getKeyword()),OscOffer::getStuName, dto.getKeyword())
				.orderByDesc(OscOffer::getOfferSort)

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
		return R.ok(iOscOfferService.getBaseMapper().selectById(id));
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
