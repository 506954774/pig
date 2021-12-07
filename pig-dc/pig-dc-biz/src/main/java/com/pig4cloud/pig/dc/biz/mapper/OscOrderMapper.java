package com.pig4cloud.pig.dc.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.dc.api.dto.QueryOrderPageDTO;
import com.pig4cloud.pig.dc.api.dto.WebQueryMajorPageDTO;
import com.pig4cloud.pig.dc.api.entity.OscOrder;
import com.pig4cloud.pig.dc.api.vo.OrderCountVo;
import com.pig4cloud.pig.dc.api.vo.OrderVo;
import com.pig4cloud.pig.dc.api.vo.OscMajorVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 *
 * @author chenlei
 * @since 2021-12-05
 */
@Mapper

public interface OscOrderMapper extends BaseMapper<OscOrder> {



	/**
	 * @Name:
	 * @Description: 查询订单分页列表
	 *
	 * @Param:
	 * @return:
	 * @Author: LeiChen
	 * @Date:2021/12/6 22:07
	 *
	 * */
	Page<OrderVo> selectOrders(IPage page, @Param("param") QueryOrderPageDTO param);


	/**
	 * @Name:
	 * @Description: 查询订单数量统计
	 * @Param:
	 * @return:
	 * @Author: LeiChen
	 * @Date:2021/12/6 22:18
	 *
	 * */
	List<OrderCountVo> selectOrdersCount(@Param("param") QueryOrderPageDTO param);

	/**
	 * @Name:
	 * @Description: 根据id查询详情
	 * @Param:
	 * @return:
	 * @Author: LeiChen
	 * @Date:2021/12/7 19:34
	 *
	 * */
	OrderVo selectOrderById ( @Param("id") Integer id);
}
