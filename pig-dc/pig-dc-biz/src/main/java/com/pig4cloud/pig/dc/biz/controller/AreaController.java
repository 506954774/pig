package com.pig4cloud.pig.dc.biz.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.dc.api.dto.AddOscAreaDTO;
import com.pig4cloud.pig.dc.api.dto.AddOscBannerDTO;
import com.pig4cloud.pig.dc.api.dto.QueryPageDTO;
import com.pig4cloud.pig.dc.api.entity.OscAdministrativeDivision;
import com.pig4cloud.pig.dc.api.entity.OscArea;
import com.pig4cloud.pig.dc.api.entity.OscBanner;
import com.pig4cloud.pig.dc.api.vo.OscAdministrativeDivisionVo;
import com.pig4cloud.pig.dc.biz.exceptions.BizException;
import com.pig4cloud.pig.dc.biz.service.IOscAdministrativeDivisionService;
import com.pig4cloud.pig.dc.biz.service.IOscAreaService;
import com.pig4cloud.pig.dc.biz.utils.ThreeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.http.util.TextUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @author LeiChen
 * @version 1.0
 * @date 2021/5/21 12:37
 */
@RestController
@AllArgsConstructor
@RequestMapping("/area")
@Api(value = "AreaController", tags = "地区查询接口")
public class AreaController {

	private final IOscAreaService iOscAreaService;
	private final IOscAdministrativeDivisionService iOscAdministrativeDivisionService;



	/**
	 * 新增地区
	 * @param dto
	 * @return
	 */
	@ApiOperation(value = "新增地区 ", notes = "新增地区 ")
	@PostMapping("/add")
	public R save(  @RequestBody @Valid AddOscAreaDTO dto) {

		List<OscArea> oscAreas = iOscAreaService.getBaseMapper().selectList(Wrappers.<OscArea>query().lambda().eq(OscArea::getAreaName, dto.getAreaName()));
		if(CollectionUtils.isNotEmpty(oscAreas)){
			throw new BizException("该地区已存在");
		}
		OscArea entity= BeanUtil.copyProperties(dto,OscArea.class);
		entity.setCreateTime(new Date());
		entity.setCreateBy(SecurityUtils.getUser().getId()+"");
		iOscAreaService.getBaseMapper().insert(entity);
		return R.ok(entity.getId());
	}

	/**
	 * 通过id删除地区
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除地区", notes = "通过id删除地区")
	@DeleteMapping("/{id}" )
	public R removeById(@PathVariable Integer id) {
		return R.ok(iOscAreaService.getBaseMapper().deleteById(id));
	}


	/**
	 * 修改地区
	 * @param dto
	 * @return
	 */
	@ApiOperation(value = "修改地区 ", notes = "修改地区 ")
	@PostMapping("/update")
	public R update( @RequestBody @Valid OscArea dto) {
		iOscAreaService.updateById(dto);
		return R.ok(null);
	}

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


	/**
	 *  查询地区树形列表(国家-省-市-区)
	 *
	 * @return
	 */
	@ApiOperation(response = OscAdministrativeDivision.class ,value = " 查询地区树形列表(国家-省-市-区)", notes = " 查询地区树形列表(国家-省-市-区)")
	@PostMapping("/tree")
	public R tree() {

		List<OscAdministrativeDivision> oscAdministrativeDivisions = iOscAdministrativeDivisionService.getBaseMapper().selectList(Wrappers.emptyWrapper());
		if(CollectionUtils.isNotEmpty(oscAdministrativeDivisions)){
            List<OscAdministrativeDivisionVo>  vos= BeanUtil.copyToList(oscAdministrativeDivisions,OscAdministrativeDivisionVo.class);
			return R.ok(ThreeUtil.getTree(vos,0));
		}
		return R.ok(null);
	}




}
