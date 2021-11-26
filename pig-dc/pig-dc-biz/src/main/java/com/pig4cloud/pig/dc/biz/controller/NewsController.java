package com.pig4cloud.pig.dc.biz.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.dc.api.dto.AddNewsDTO;
import com.pig4cloud.pig.dc.api.dto.QueryPageDTO;
import com.pig4cloud.pig.dc.api.entity.OscEnrollInfo;
import com.pig4cloud.pig.dc.api.entity.OscNews;
import com.pig4cloud.pig.dc.biz.service.IOscNewsService;
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
@RequestMapping("/news")
@Api(value = "NewsController", tags = "新闻资讯管理")
public class NewsController {

	private final IOscNewsService iOscNewsService;


	/**
	 * 新增新闻资讯
	 * @param dto
	 * @return
	 */
	@ApiOperation(value = "新增新闻资讯 ", notes = "新增新闻资讯 ")
	@PostMapping("/add")
	public R save(  @RequestBody @Valid AddNewsDTO dto) {
		OscNews entity= BeanUtil.copyProperties(dto,OscNews.class);
		entity.setCreateTime(new Date());
		entity.setCreateBy(SecurityUtils.getUser().getId()+"");
		return R.ok(iOscNewsService.save(entity ));
	}


	/**
	 * 通过id删除新闻资讯
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除新闻资讯", notes = "通过id删除新闻资讯")
	@DeleteMapping("/{id}" )
	public R removeById(@PathVariable Integer id) {
		return R.ok(iOscNewsService.getBaseMapper().deleteById(id));
	}


	/**
	 * 修改新闻资讯
	 * @param dto
	 * @return
	 */
	@ApiOperation(value = "修改新闻资讯 ", notes = "修改新闻资讯 ")
	@PostMapping("/update")
	public R update( @RequestBody @Valid OscNews dto) {
		iOscNewsService.updateById(dto);
		return R.ok(null);
	}



	/**
	 *  新闻资讯分页列表
	 *
	 * @return
	 */
	@ApiOperation(value = " 新闻资讯分页列表", notes = " 新闻资讯分页列表")
	@PostMapping("/page")
	public R page(@RequestBody @Valid QueryPageDTO dto) {
		if(dto==null){
			dto=new QueryPageDTO();
		}
		Page page=new Page();
		page.setCurrent(dto.getCurrent());
		page.setSize(dto.getSize());
		final QueryPageDTO finalDto = dto;
		Page page1 = iOscNewsService.getBaseMapper().selectPage(
				page, Wrappers.<OscNews>query().lambda()
						.and(!TextUtils.isEmpty(dto.getKeyword()),wrapper -> wrapper
								. like(OscNews::getTitle, finalDto.getKeyword())
								.or(). like(OscNews::getWebUrl, finalDto.getKeyword()))
						//.like(!TextUtils.isEmpty(dto.getKeyword()),OscNews::getTitle, dto.getKeyword())
				.orderByDesc(OscNews::getCreateTime)

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
		return R.ok(iOscNewsService.getBaseMapper().selectById(id));
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
