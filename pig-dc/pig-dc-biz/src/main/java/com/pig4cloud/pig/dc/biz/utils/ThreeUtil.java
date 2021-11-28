package com.pig4cloud.pig.dc.biz.utils;
import com.pig4cloud.pig.dc.api.vo.OscAdministrativeDivisionVo;

import java.util.ArrayList;
import java.util.List;

/**
 * ThreeUtil
 * 责任人:  ChenLei
 * 修改人： ChenLei
 * 创建/修改时间: 2021/11/28 11:04
 * Copyright :  版权所有
 **/


public class ThreeUtil {
	/**
	 * 递归创建树形结构
	 */
	public static List<OscAdministrativeDivisionVo> getTree(List<OscAdministrativeDivisionVo> nodeList, Integer parentId) {
		List<OscAdministrativeDivisionVo> threeNodeList = new ArrayList<>() ;
		for (OscAdministrativeDivisionVo entity : nodeList) {
			Integer nodeId = entity.getId() ;
			Integer nodeParentId = entity.getParentId() ;
			if (parentId.intValue() == nodeParentId.intValue()) {
				List<OscAdministrativeDivisionVo> childList = getTree(nodeList,nodeId) ;
				if (childList != null && childList.size()>0){
					entity.setChildren(childList);
					entity.setChildNodeSize(childList.size());
				}
				threeNodeList.add(entity) ;
			}
		}
		return threeNodeList ;
	}

	/**
	 * 获取指定子节点
	 */
	private static List<OscAdministrativeDivisionVo> getChildTree (Integer id,List<OscAdministrativeDivisionVo> nodeList){
		List<OscAdministrativeDivisionVo> resultList = new ArrayList<>();
		for (OscAdministrativeDivisionVo entity : nodeList) {
			if (entity.getParentId().intValue() == id) {
				List<OscAdministrativeDivisionVo> childList = getChildTree(entity.getId(),nodeList) ;
				entity.setChildren(childList);
				entity.setChildNodeSize(childList.size());
				resultList.add(entity) ;
			}
		}
		return resultList ;
	}

	/**
	 * 遍历树形结构
	 */
	private static transient List<Integer> treeIdList = new ArrayList<>() ;
	private static List<Integer> getTreeInfo (List<OscAdministrativeDivisionVo> treeList){
		for (OscAdministrativeDivisionVo entity : treeList) {
			if (entity.getChildNodeSize()!=null && entity.getChildNodeSize()>0){
				getTreeInfo(entity.getChildren());
			}
			treeIdList.add(entity.getId());
		}
		return treeIdList ;
	}

	/**
	 * 判断节是否是叶子节点
	 */
	private static boolean hasChildNode (Integer id,List<OscAdministrativeDivisionVo> nodeList){
		for (OscAdministrativeDivisionVo entity:nodeList){
			if (entity.getParentId().intValue() == id){
				return true ;
			}
		}
		return false ;
	}

	public static void main(String[] args) {
		List<OscAdministrativeDivisionVo> threeNodeList = new ArrayList<>() ;
		threeNodeList.add(new OscAdministrativeDivisionVo(1,"节点A",0)) ;
		threeNodeList.add(new OscAdministrativeDivisionVo(2,"节点B",0)) ;
		threeNodeList.add(new OscAdministrativeDivisionVo(3,"节点C",1)) ;
		threeNodeList.add(new OscAdministrativeDivisionVo(4,"节点D",1)) ;
		threeNodeList.add(new OscAdministrativeDivisionVo(5,"节点E",2)) ;
		threeNodeList.add(new OscAdministrativeDivisionVo(6,"节点F",2)) ;
		// 测试1
		List<OscAdministrativeDivisionVo> getTree = getTree(threeNodeList,0) ;
		System.out.println(getTree);
		// 测试2
		// List<ThreeNode> getChildTree = getChildTree(2,threeNodeList) ;
		// System.out.println(getChildTree);
		// 测试3
		List<Integer> treeIdList = getTreeInfo(getTree) ;
		System.out.println(treeIdList);
		// 测试4
		System.out.println(hasChildNode(2,threeNodeList)) ;
	}
}