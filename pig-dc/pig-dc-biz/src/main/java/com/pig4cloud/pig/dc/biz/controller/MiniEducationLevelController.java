package com.pig4cloud.pig.dc.biz.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.dc.api.dto.AddEducationLevelDTO;
import com.pig4cloud.pig.dc.api.dto.QueryEducationLevelPageDTO;
import com.pig4cloud.pig.dc.api.dto.QueryPageDTO;
import com.pig4cloud.pig.dc.api.entity.OscEducationLevel;
import com.pig4cloud.pig.dc.biz.service.IOscEducationLevelService;
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
@RequestMapping("/mini/education_level")
@Api(value = "MiniEducationLevelController", tags = "小程序-留学阶段")
public class MiniEducationLevelController {

	private final IOscEducationLevelService iOscEducationLevelService;





	/**
	 *  留学阶段分页列表
	 *
	 * @return
	 */
	@ApiOperation(value = " 留学阶段分页列表", notes = " 留学阶段分页列表")
	@PostMapping("/page")
	public R page(@RequestBody @Valid QueryEducationLevelPageDTO dto) {
		if(dto==null){
			dto=new QueryEducationLevelPageDTO();
		}
		Page page=new Page();
		page.setCurrent(dto.getCurrent());
		page.setSize(dto.getSize());
		final QueryEducationLevelPageDTO finalDto = dto;
		Page page1 = iOscEducationLevelService.getBaseMapper().selectPage(
				page, Wrappers.<OscEducationLevel>query().lambda()
						.eq(dto.getEducationType()!=null,OscEducationLevel::getEducationType,dto.getEducationType())
						.and(!TextUtils.isEmpty(dto.getKeyword()),

								wrapper1->wrapper1.like(OscEducationLevel::getTitle, finalDto.getKeyword())
								.or(

								wrapper -> wrapper
								. like(OscEducationLevel::getIocnUrl, finalDto.getKeyword())
								.or(). like(OscEducationLevel::getContent, finalDto.getKeyword()
										)
								)
						)
						//.like(!TextUtils.isEmpty(dto.getKeyword()),OscEducationLevel::getStuName, dto.getKeyword())
				.orderByDesc(OscEducationLevel::getEducationLevelSort)

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
		return R.ok(iOscEducationLevelService.getBaseMapper().selectById(id));
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
