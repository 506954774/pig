package com.pig4cloud.pig.dc.biz.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.dc.api.dto.AddOscMajorDTO;
import com.pig4cloud.pig.dc.api.dto.WebQueryMajorPageDTO;
import com.pig4cloud.pig.dc.api.entity.OscMajor;
import com.pig4cloud.pig.dc.biz.mapper.OscMajorMapper;
import com.pig4cloud.pig.dc.biz.service.IOscMajorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
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
@RequestMapping("/mini/major")
@Api(value = "MiniMajorController", tags = "小程序-专业模块")
public class MiniMajorController {

	private final IOscMajorService iOscMajorService;
	private final OscMajorMapper oscMajorMapper;





	/**
	 *  专业分页列表
	 *
	 * @return
	 */
	@ApiOperation(value = " 专业分页列表", notes = " 专业分页列表")
	@PostMapping("/page")
	public R page(@RequestBody @Valid WebQueryMajorPageDTO dto) {
		if(dto==null){
			dto=new WebQueryMajorPageDTO();
		}
		Page page=new Page();
		page.setCurrent(dto.getCurrent());
		page.setSize(dto.getSize());
		Page page1 = oscMajorMapper.selectMajorList(page, dto);
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
		return R.ok(oscMajorMapper.selectMajor(id));
	}



}
