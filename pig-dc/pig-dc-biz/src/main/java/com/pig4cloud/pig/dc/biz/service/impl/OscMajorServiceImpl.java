package com.pig4cloud.pig.dc.biz.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.dc.api.dto.AddOscMajorDTO2;
import com.pig4cloud.pig.dc.api.dto.BatchAddOscMajorDTO;
import com.pig4cloud.pig.dc.api.entity.OscCollege;
import com.pig4cloud.pig.dc.api.entity.OscMajor;
import com.pig4cloud.pig.dc.api.entity.OscUniversity;
import com.pig4cloud.pig.dc.biz.exceptions.BizException;
import com.pig4cloud.pig.dc.biz.mapper.*;
import com.pig4cloud.pig.dc.biz.service.IOscMajorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

	@Override
	public Integer batchSave(BatchAddOscMajorDTO dto) {

		StringBuilder error=new StringBuilder();

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
			universityMaps.put(oscUniversities.get(0).getUniversityName(),oscUniversities.get(0).getId());
		}
		if(error.length()!=0){
			throw new BizException(error.toString());
		}

		for (AddOscMajorDTO2 dto2:dto.getList()){
			 //判断学院是否存在,不存在则新增
			OscCollege oscCollege = oscCollegeMapper.selectOscCollegeByName(dto2.getCollegeName(), universityMaps.get(dto2.getUniversityName()));

		}

		return null;
	}
}
