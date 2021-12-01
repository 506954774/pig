package com.pig4cloud.pig.dc.biz.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.dc.api.dto.QueryPageDTO;
import com.pig4cloud.pig.dc.api.entity.OscArea;
import com.pig4cloud.pig.dc.biz.service.IOscAdministrativeDivisionService;
import com.pig4cloud.pig.dc.biz.service.IOscAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.http.util.TextUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author LeiChen
 * @version 1.0
 * @date 2021/5/21 12:37
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mini/area")
@Api(value = "MiniAreaController", tags = "小程序-地区查询接口")
public class MiniAreaController {

	private final IOscAreaService iOscAreaService;
	private final IOscAdministrativeDivisionService iOscAdministrativeDivisionService;



	/**
	 *  查询地区列表(国家/地区列表)
	 *
	 * @return
	 */
	@ApiOperation(value = " 查询地区列表(国家/地区列表)", notes = " 查询地区列表(国家/地区列表)")
	@PostMapping("/page")
	public R page(@RequestBody @Valid QueryPageDTO dto) {
		if(dto==null){
			dto=new QueryPageDTO();
		}
		Page page=new Page();
		page.setCurrent(dto.getCurrent());
		page.setSize(dto.getSize());
		//final QueryPageDTO finalDto = dto;
		Page page1 = iOscAreaService.getBaseMapper().selectPage(
				page, Wrappers.<OscArea>query().lambda()
						/*.and(!TextUtils.isEmpty(dto.getKeyword()),wrapper -> wrapper. like(OscBanner::getTitle, finalDto.getKeyword())
								.or(). like(OscBanner::get, finalDto.getKeyword()))*/
						.like(!TextUtils.isEmpty(dto.getKeyword()),OscArea::getAreaName, dto.getKeyword())
				.orderByDesc(OscArea::getAreaSort)

		);
		return R.ok(page1);
	}





}
