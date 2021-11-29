package com.pig4cloud.pig.dc.biz.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.dc.api.dto.AddOscCollegeDTO;
import com.pig4cloud.pig.dc.api.dto.QueryCollegePageDTO;
import com.pig4cloud.pig.dc.api.entity.OscCollege;
import com.pig4cloud.pig.dc.api.entity.OscUniversity;
import com.pig4cloud.pig.dc.api.entity.OscUniversityCollege;
import com.pig4cloud.pig.dc.api.vo.OscUniversityDetailsVo;
import com.pig4cloud.pig.dc.biz.exceptions.BizException;
import com.pig4cloud.pig.dc.biz.mapper.OscCollegeMapper;
import com.pig4cloud.pig.dc.biz.mapper.OscUniversityCollegeMapper;
import com.pig4cloud.pig.dc.biz.mapper.OscUniversityMapper;
import com.pig4cloud.pig.dc.biz.mapper.SysDeptRelationMapper;
import com.pig4cloud.pig.dc.biz.service.IOscUniversityService;
import lombok.RequiredArgsConstructor;
import org.apache.http.util.TextUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 大学 服务实现类
 * </p>
 *
 * @author chenlei
 * @since 2021-11-28
 */
@Service
@RequiredArgsConstructor
public class OscUniversityServiceImpl extends ServiceImpl<OscUniversityMapper, OscUniversity> implements IOscUniversityService {

	private final OscUniversityCollegeMapper oscUniversityCollegeMapper;
	private final OscCollegeMapper oscCollegeMapper;


	@Override
	public OscUniversityDetailsVo details(Integer id) {
		OscUniversity oscUniversity = getBaseMapper().selectById(id);
		if(oscUniversity!=null){
			OscUniversityDetailsVo result= BeanUtil.copyProperties(oscUniversity,OscUniversityDetailsVo.class);
			List<OscUniversityCollege> oscUniversityColleges = oscUniversityCollegeMapper.selectList(Wrappers.<OscUniversityCollege>query().lambda()
					.eq(OscUniversityCollege::getUniversityId, oscUniversity.getId())
			);
			if(CollectionUtils.isNotEmpty(oscUniversityColleges)){
				List<OscCollege> oscColleges = oscCollegeMapper.selectList(Wrappers.<OscCollege>query().lambda()
						.in(OscCollege::getId, oscUniversityColleges.stream().map(bean -> bean.getCollegeId()).collect(Collectors.toList()))
				);
				result.setCooleges(oscColleges);
			}
			return result;
		}
		return null;
	}

	@Override
	public List<OscCollege> queryCollegesByUniversityId(Integer id) {
		OscUniversity oscUniversity = getBaseMapper().selectById(id);
		if(oscUniversity!=null){
			List<OscUniversityCollege> oscUniversityColleges = oscUniversityCollegeMapper.selectList(Wrappers.<OscUniversityCollege>query().lambda()
					.eq(OscUniversityCollege::getUniversityId, oscUniversity.getId())
			);
			if(CollectionUtils.isNotEmpty(oscUniversityColleges)){
				List<OscCollege> oscColleges = oscCollegeMapper.selectList(Wrappers.<OscCollege>query().lambda()
						.in(OscCollege::getId, oscUniversityColleges.stream().map(bean -> bean.getCollegeId()).collect(Collectors.toList()))
				);
				return oscColleges;
			}
		}
		return null;
	}

	@Override
	public Page<OscCollege> queryCollegesPageByUniversityId(QueryCollegePageDTO dto) {
		Page page=new Page();
		page.setCurrent(dto.getCurrent());
		page.setSize(dto.getSize());
		OscUniversity oscUniversity = getBaseMapper().selectById(dto.getUniverstyId());
		if(oscUniversity!=null){
			List<OscUniversityCollege> oscUniversityColleges = oscUniversityCollegeMapper.selectList(Wrappers.<OscUniversityCollege>query().lambda()
					.eq(OscUniversityCollege::getUniversityId, oscUniversity.getId())
			);
			if(CollectionUtils.isNotEmpty(oscUniversityColleges)){
				return oscCollegeMapper.selectPage(page,Wrappers.<OscCollege>query().lambda()
						.like(!TextUtils.isEmpty(dto.getKeyword()),OscCollege::getCollegeName,dto.getKeyword())
						.in(OscCollege::getId, oscUniversityColleges.stream().map(bean -> bean.getCollegeId()).collect(Collectors.toList()))
				);
			}
		}
		return page;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Integer insertCollege(AddOscCollegeDTO dto) {
		OscUniversity oscUniversity = getBaseMapper().selectById(dto.getUniverstyId());
		if(oscUniversity==null){
			throw new BizException("大学不存在或已被删除");
		}
		List<OscCollege> list=queryCollegesByUniversityId(dto.getUniverstyId());
		if(CollectionUtils.isNotEmpty(list)){
			List<OscCollege> collect = list.stream().filter(bean -> bean.getCollegeName().equals(dto.getCollegeName())).collect(Collectors.toList());
			if(CollectionUtils.isNotEmpty(collect)){
				throw new BizException("同名的学院已经存在");
			}
		}
		OscCollege entity=BeanUtil.copyProperties(dto,OscCollege.class);
		entity.setCreateBy(SecurityUtils.getUser().getId()+"");
		oscCollegeMapper.insert(entity);

		OscUniversityCollege ralations=new OscUniversityCollege();
		ralations.setUniversityId(dto.getUniverstyId());
		ralations.setCollegeId(entity.getId());
		oscUniversityCollegeMapper.insert(ralations);

		return entity.getId();
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Integer deleteCollegeById(Integer id) {
		OscCollege oscCollege = oscCollegeMapper.selectById(id);
		if(oscCollege==null){
			throw new BizException("该学院不存在或者已被删除");
		}
		oscUniversityCollegeMapper.delete(Wrappers.<OscUniversityCollege>query().lambda().eq(OscUniversityCollege::getCollegeId, id));
		oscCollegeMapper.deleteById(id);
		return null;
	}
}
