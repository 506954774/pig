package com.pig4cloud.pig.dc.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.dc.api.dto.WebQueryMajorPageDTO;
import com.pig4cloud.pig.dc.api.entity.OscMajor;
import com.pig4cloud.pig.dc.api.vo.OscMajorDetailsVo;
import com.pig4cloud.pig.dc.api.vo.OscMajorVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 专业 Mapper 接口
 * </p>
 *
 * @author chenlei
 * @since 2021-11-28
 */
@Mapper

public interface OscMajorMapper extends BaseMapper<OscMajor> {

	/**
	 * @Name:
	 * @Description: 查询列表
	 * @Param:
	 * @return:
	 * @Author: LeiChen
	 * @Date:2021/11/28 18:13
	 *
	 * */
	Page<OscMajorVo >  selectMajorList(IPage page,@Param("param") WebQueryMajorPageDTO param);

	/**
	 * @Name:
	 * @Description: 按id查询详情
	 * @Param:
	 * @return:
	 * @Author: LeiChen
	 * @Date:2021/11/28 18:24
	 *
	 * */
	OscMajorDetailsVo selectMajor (  @Param("id") Integer id);
}
