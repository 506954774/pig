package com.pig4cloud.pig.dc.biz.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.dc.api.dto.QueryIndexPageDTO;
import com.pig4cloud.pig.dc.api.dto.QueryPageDTO;
import com.pig4cloud.pig.dc.api.dto.WebQueryMajorPageDTO;
import com.pig4cloud.pig.dc.api.entity.*;
import com.pig4cloud.pig.dc.api.vo.OrderVo;
import com.pig4cloud.pig.dc.biz.mapper.OscMajorMapper;
import com.pig4cloud.pig.dc.biz.service.*;
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
@RequestMapping("/mini/index")
@Api(value = "MiniIndexController", tags = "小程序-首页相关接口")
public class MiniIndexController {

	private final IOscBannerService iOscBannerService;
	private final IOscIndexGuideService iOscIndexGuideService;
	private final IOscOfferService iOscOfferService;
	private final IOscProductService iOscProductService;
	private final IOscNewsService iOscNewsService;

	private final IOscMajorService iOscMajorService;
	private final OscMajorMapper oscMajorMapper;



	/**
	 *  轮播图分页列表
	 *
	 * @return
	 */
	@ApiOperation(value = " 轮播图分页列表", notes = " 轮播图分页列表")
	@PostMapping("/banner/page")
	public R queryBannerPage(@RequestBody @Valid QueryPageDTO dto) {
		if(dto==null){
			dto=new QueryPageDTO();
		}
		Page page=new Page();
		page.setCurrent(dto.getCurrent());
		page.setSize(dto.getSize());
		final QueryPageDTO finalDto = dto;
		Page page1 = iOscBannerService.getBaseMapper().selectPage(
				page, Wrappers.<OscBanner>query().lambda()
						/*.and(!TextUtils.isEmpty(dto.getKeyword()),wrapper -> wrapper. like(OscBanner::getTitle, finalDto.getKeyword())
								.or(). like(OscBanner::get, finalDto.getKeyword()))*/
						.like(!TextUtils.isEmpty(dto.getKeyword()),OscBanner::getTitle, dto.getKeyword())
						.orderByDesc(OscBanner::getBannerSort)

		);
		return R.ok(page1);
	}



	/**
	 *  首页导航分页列表
	 *
	 * @return
	 */
	@ApiOperation(value = " 首页导航分页列表", notes = " 首页导航分页列表")
	@PostMapping("/guide/page")
	public R queryGuidePage(@RequestBody @Valid QueryPageDTO dto) {
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
	 *  offer榜单分页列表(首页推荐)
	 *
	 * @return
	 */
	@ApiOperation(value = " offer榜单分页列表(首页推荐)", notes = " offer榜单分页列表(首页推荐)")
	@PostMapping("/offer/page")
	public R queryOfferPage(@RequestBody @Valid QueryPageDTO dto) {
		if(dto==null){
			dto=new QueryPageDTO();
		}
		Page page=new Page();
		page.setCurrent(dto.getCurrent());
		page.setSize(dto.getSize());
		final QueryPageDTO finalDto = dto;
		Page page1 = iOscOfferService.getBaseMapper().selectPage(
				page, Wrappers.<OscOffer>query().lambda()
						.eq(OscOffer::getRecommendFlag,1)
						.and(!TextUtils.isEmpty(dto.getKeyword()),

								wrapper1->wrapper1.like(OscOffer::getSchoolDesc, finalDto.getKeyword())
										.or(

												wrapper -> wrapper
														. like(OscOffer::getStuName, finalDto.getKeyword())
														.or(). like(OscOffer::getMajorName, finalDto.getKeyword()
														)
										)
						)
						//.like(!TextUtils.isEmpty(dto.getKeyword()),OscOffer::getStuName, dto.getKeyword())
						.orderByDesc(OscOffer::getOfferSort)

		);
		return R.ok(page1);
	}


	/**
	 *  产品信息分页列表(首页推荐)
	 *
	 * @return
	 */
	@ApiOperation(value = " 产品信息分页列表(首页推荐)", notes = " 产品信息分页列表(首页推荐)")
	@PostMapping("/product/page")
	public R queryProductPage(@RequestBody @Valid QueryPageDTO dto) {
		if(dto==null){
			dto=new QueryPageDTO();
		}
		Page page=new Page();
		page.setCurrent(dto.getCurrent());
		page.setSize(dto.getSize());
		final QueryPageDTO finalDto = dto;
		Page page1 = iOscProductService.getBaseMapper().selectPage(
				page, Wrappers.<OscProduct>query().lambda()
						.eq(OscProduct::getRecommendFlag,1)
						.and(!TextUtils.isEmpty(dto.getKeyword()),wrapper -> wrapper
								. like(OscProduct::getProductName, finalDto.getKeyword())
								.or(). like(OscProduct::getProductSubName, finalDto.getKeyword()))
						//.like(!TextUtils.isEmpty(dto.getKeyword()),OscProduct::getProductName, dto.getKeyword())
						.orderByDesc(OscProduct::getProductPrice)

		);
		return R.ok(page1);
	}


	/**
	 *  新闻资讯分页列表(首页推荐)
	 *
	 * @return
	 */
	@ApiOperation(value = " 新闻资讯分页列表(首页推荐)", notes = " 新闻资讯分页列表(首页推荐)")
	@PostMapping("/news/page")
	public R queryNewsPage(@RequestBody @Valid QueryPageDTO dto) {
		if(dto==null){
			dto=new QueryPageDTO();
		}
		Page page=new Page();
		page.setCurrent(dto.getCurrent());
		page.setSize(dto.getSize());
		final QueryPageDTO finalDto = dto;
		Page page1 = iOscNewsService.getBaseMapper().selectPage(
				page, Wrappers.<OscNews>query().lambda()
						.eq(OscNews::getRecommendFlag,1)
						.and(!TextUtils.isEmpty(dto.getKeyword()),wrapper -> wrapper
								. like(OscNews::getTitle, finalDto.getKeyword())
								.or(). like(OscNews::getWebUrl, finalDto.getKeyword()))
						//.like(!TextUtils.isEmpty(dto.getKeyword()),OscNews::getTitle, dto.getKeyword())
						.orderByDesc(OscNews::getCreateTime)

		);
		return R.ok(page1);
	}

	/**
	 *  全局搜索分页列表
	 *
	 * @return
	 */
	@ApiOperation(value = "全局搜索分页列表", notes = "全局搜索分页列表")
	@PostMapping("/search")
	public R queryOfferPage(@RequestBody @Valid QueryIndexPageDTO dto) {
		if(dto==null){
			dto=new QueryIndexPageDTO();
		}
		Page page=new Page();
		page.setCurrent(dto.getCurrent());
		page.setSize(dto.getSize());
		Page page1 = oscMajorMapper.search(page,dto);
		return R.ok(page1);
	}


	/**
	 * 根据id查询导航详情
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "根据id查询导航详情", notes = "根据id查询导航详情" )
	@GetMapping("/guide/details/{id}" )
	public R getGuideDetails(@PathVariable Integer id) {
		return R.ok(iOscIndexGuideService.getBaseMapper().selectById(id));
	}


}
