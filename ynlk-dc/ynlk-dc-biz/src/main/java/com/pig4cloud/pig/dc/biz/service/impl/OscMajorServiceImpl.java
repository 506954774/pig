package com.pig4cloud.pig.dc.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.dc.api.dto.AddOscMajorDTO2;
import com.pig4cloud.pig.dc.api.dto.BatchAddOscMajorDTO;
import com.pig4cloud.pig.dc.api.entity.OscCollege;
import com.pig4cloud.pig.dc.api.entity.OscMajor;
import com.pig4cloud.pig.dc.api.entity.OscUniversity;
import com.pig4cloud.pig.dc.api.entity.OscUniversityCollege;
import com.pig4cloud.pig.dc.biz.exceptions.BizException;
import com.pig4cloud.pig.dc.biz.mapper.*;
import com.pig4cloud.pig.dc.biz.service.IOscMajorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 专业 服务实现类
 * </p>
 *
 * @author chenlei
 * @since 2021-11-28
 */
@Service
@RequiredArgsConstructor
public class OscMajorServiceImpl extends ServiceImpl<OscMajorMapper, OscMajor> implements IOscMajorService {

	private final OscUniversityMapper oscUniversityMapper;
	private final OscUniversityCollegeMapper oscUniversityCollegeMapper;
	private final OscMajorMapper oscMajorMapper;
	private final OscCollegeMapper oscCollegeMapper;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Integer batchSave(BatchAddOscMajorDTO dto) {

		StringBuilder error=new StringBuilder();

		//key是大学名称,value是大学id
		HashMap<String,Integer> universityMaps=new HashMap<>();

		for (AddOscMajorDTO2 dto2:dto.getList()){
			//先判断学校是否存在
			List<OscUniversity> oscUniversities = oscUniversityMapper
					.selectList(Wrappers.<OscUniversity>query().lambda().eq(OscUniversity::getUniversityName, dto2.getUniversityName())
					.orderByDesc(OscUniversity::getCreateTime)
					);
			if(CollectionUtils.isEmpty(oscUniversities)){
				error.append("学校不存在:"+dto2.getUniversityName());
			}
			else{
				universityMaps.put(oscUniversities.get(0).getUniversityName(),oscUniversities.get(0).getId());
			}
		}
		if(error.length()!=0){
			throw new BizException(error.toString());
		}

		for (AddOscMajorDTO2 dto2:dto.getList()){
			 //判断学院是否存在,不存在则新增
			OscCollege oscCollege = oscCollegeMapper.selectOscCollegeByName(dto2.getCollegeName(), universityMaps.get(dto2.getUniversityName()));
			if(oscCollege!=null){

			}
			else {
				//先插入一条学院数据
				oscCollege=new OscCollege();
				oscCollege.setCollegeName(dto2.getCollegeName());
				oscCollegeMapper.insert(oscCollege);

				//再插入一条关联数据
				OscUniversityCollege ralations=new OscUniversityCollege();
				ralations.setUniversityId(universityMaps.get(dto2.getUniversityName()));
				ralations.setCollegeId(oscCollege.getId());
				oscUniversityCollegeMapper.insert(ralations);
			}

			OscMajor entity= BeanUtil.copyProperties(dto2,OscMajor.class);
			entity.setUniversityId(universityMaps.get(dto2.getUniversityName()));
			entity.setCollegeId(oscCollege.getId());
			entity.setCreateTime(new Date());
			entity.setCreateBy(SecurityUtils.getUser().getId()+"");
			//insert
			getBaseMapper().insert(entity);
		}

		return dto.getList().size();
	}
}
