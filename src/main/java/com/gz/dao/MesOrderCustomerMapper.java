package com.gz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gz.beans.PageQuery;
import com.gz.dto.SearchOrderDto;
import com.gz.model.MesOrder;



public interface MesOrderCustomerMapper {
	Long getOrderCount();

	// @Param("dto")--给mapper.xml查询sql指定参数名称 #{dto.keyword}
	int countBySearchDto(@Param("dto") SearchOrderDto dto);

	List<MesOrder> getPageListBySearchDto(@Param("dto") SearchOrderDto dto, @Param("page") PageQuery page);

	MesOrder getOrderByOid(@Param("pid") String pid);

	void batchStart(@Param("list")String[] idArray);

	MesOrder selectByOrderId(@Param("orderid")String orderid);
}