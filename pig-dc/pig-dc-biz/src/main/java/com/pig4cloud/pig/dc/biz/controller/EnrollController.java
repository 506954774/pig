package com.pig4cloud.pig.dc.biz.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.dc.api.dto.QueryPageDTO;
import com.pig4cloud.pig.dc.api.entity.OscEnrollInfo;
import com.pig4cloud.pig.dc.biz.config.Constant;
import com.pig4cloud.pig.dc.biz.service.IOscEnrollInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.http.util.TextUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author LeiChen
 * @version 1.0
 * @date 2021/5/21 12:37
 */
@RestController
@AllArgsConstructor
@RequestMapping("/message")
@Api(value = "EnrollController", tags = "留言管理")
public class EnrollController {

	private final IOscEnrollInfoService iOscBannerService;

	/**
	 * 通过id删除留言
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除留言", notes = "通过id删除留言")
	@DeleteMapping("/{id}" )
	public R removeById(@PathVariable Integer id) {
		return R.ok(iOscBannerService.getBaseMapper().deleteById(id));
	}






	/**
	 *  留言分页列表
	 *
	 * @return
	 */
	@ApiOperation(value = " 留言分页列表", notes = " 留言分页列表")
	@PostMapping("/page")
	public R page(@RequestBody @Valid QueryPageDTO dto) {
		if(dto==null){
			dto=new QueryPageDTO();
		}
		Page page=new Page();
		page.setCurrent(dto.getCurrent());
		page.setSize(dto.getSize());
		Page page1 = iOscBannerService.getBaseMapper().selectPage(
				page, Wrappers.<OscEnrollInfo>query().lambda()
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


	/**
	 * 处理留言
	 * @return
	 */
	@ApiOperation(value = "处理留言 ", notes = "处理留言 ")
	@GetMapping("/operate/{id}" )
	public R update( @PathVariable Integer id) {
		OscEnrollInfo oscEnrollInfo = iOscBannerService.getBaseMapper().selectById(id);
		if(oscEnrollInfo!=null){
	      oscEnrollInfo.setStatus(Constant.OPERATED);
		}
		iOscBannerService.updateById(oscEnrollInfo);
		return R.ok(null);
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
