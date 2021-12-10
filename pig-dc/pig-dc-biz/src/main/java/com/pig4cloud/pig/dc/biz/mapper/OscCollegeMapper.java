package com.pig4cloud.pig.dc.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig4cloud.pig.dc.api.entity.OscCollege;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 学院 Mapper 接口
 * </p>
 *
 * @author chenlei
 * @since 2021-11-28
 */
@Mapper

public interface OscCollegeMapper extends BaseMapper<OscCollege> {

	/**
	 * @Name:
	 * @Description: 根据名称和大学id查询学院
	 * @Param:
	 * @return:
	 * @Author: LeiChen
	 * @Date:2021/12/10 17:30
	 *
	 * */

	OscCollege selectOscCollegeByName(@Param("collegeName") String collegeName,
									  @Param("universityId") Integer universityId);

}
