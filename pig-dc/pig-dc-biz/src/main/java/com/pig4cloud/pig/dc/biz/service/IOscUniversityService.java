package com.pig4cloud.pig.dc.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.dc.api.dto.AddOscCollegeDTO;
import com.pig4cloud.pig.dc.api.entity.OscCollege;
import com.pig4cloud.pig.dc.api.entity.OscUniversity;
import com.pig4cloud.pig.dc.api.vo.OscUniversityDetailsVo;

import java.util.List;

/**
 * <p>
 * 大学 服务类
 * </p>
 *
 * @author chenlei
 * @since 2021-11-28
 */
public interface IOscUniversityService extends IService<OscUniversity> {


	/**
	 * @Name:
	 * @Description: 查询详情
	 * @Param:
	 * @return:
	 * @Author: LeiChen
	 * @Date:2021/11/28 15:14
	 *
	 * */
	OscUniversityDetailsVo details (Integer id);

	/**
	 * @Name:
	 * @Description: 查询大学的学院列表
	 * @Param:
	 * @return:
	 * @Author: LeiChen
	 * @Date:2021/11/28 15:22
	 *
	 * */
	List<OscCollege> queryCollegesByUniversityId(Integer id);


	/**
	 * @Name:
	 * @Description: 添加学院
	 * @Param:
	 * @return:
	 * @Author: LeiChen
	 * @Date:2021/11/28 15:40
	 *
	 * */
	Integer insertCollege(AddOscCollegeDTO dto);

	/**
	 * @Name: 
	 * @Description: 根据id删除学院 
	 * @Param: 
	 * @return: 
	 * @Author: LeiChen
	 * @Date:2021/11/28 18:12
	 *
	 * */
	
	Integer deleteCollegeById(Integer id);
}
