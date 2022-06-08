package com.pig4cloud.pig.dc.biz.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.dc.api.dto.MemberExcelVo;
import com.pig4cloud.pig.dc.api.dto.QueryPageDTO;
import com.pig4cloud.pig.dc.api.entity.OscUserInfo;
import com.pig4cloud.pig.dc.biz.service.IOscUserInfoService;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import com.pig4cloud.plugin.excel.annotation.Sheet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.http.util.TextUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author LeiChen
 * @version 1.0
 * @date 2021/5/21 12:37
 */
@RestController
@AllArgsConstructor
@RequestMapping("/member")
@Api(value = "MemberController", tags = "会员管理")
public class MemberController {

	private final IOscUserInfoService iOscUserInfoService;

 


	/**
	 * 通过id删除会员
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除会员", notes = "通过id删除会员")
	@DeleteMapping("/{id}" )
	public R removeById(@PathVariable Integer id) {
		return R.ok(iOscUserInfoService.getBaseMapper().deleteById(id));
	}






	/**
	 *  会员分页列表
	 *
	 * @return
	 */
	@ApiOperation(value = " 会员分页列表", notes = " 会员分页列表",response = OscUserInfo.class)
	@PostMapping("/page")
	public R page(@RequestBody @Valid QueryPageDTO dto) {
		if(dto==null){
			dto=new QueryPageDTO();
		}
		Page page=new Page();
		page.setCurrent(dto.getCurrent());
		page.setSize(dto.getSize());
		final QueryPageDTO finalDto = dto;
		Page page1 = iOscUserInfoService.getBaseMapper().selectPage(
				page, Wrappers.<OscUserInfo>query().lambda()
						.and(!TextUtils.isEmpty(dto.getKeyword()),

								wrapper1->wrapper1.like(OscUserInfo::getRealname, finalDto.getKeyword())
										.or(

												wrapper -> wrapper
														. like(OscUserInfo::getNickname, finalDto.getKeyword())
														.or(). like(OscUserInfo::getPhone, finalDto.getKeyword()
														)
										)
						)
						//.like(!TextUtils.isEmpty(dto.getKeyword()),OscOffer::getStuName, dto.getKeyword())
						.orderByDesc(OscUserInfo::getCreateTime)

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
		return R.ok(iOscUserInfoService.getBaseMapper().selectById(id));
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


	/**
	 * 导出会员列表
	 * @return R
	 */

	@ResponseExcel(name = "member-list" ,sheets = {@Sheet(sheetName="会员列表")} )
	@ApiOperation(  value = " 导出会员列表", notes = " 导出会员列表")
	@PostMapping("/list/export")
	public List<MemberExcelVo> listExport( @RequestBody QueryPageDTO qo ) {
		return  iOscUserInfoService.exportMemberList(qo ) ;
	}

}
