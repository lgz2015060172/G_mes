package com.gz.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gz.beans.PageQuery;
import com.gz.dto.ProductDto;
import com.gz.dto.SearchProductDto;
import com.gz.model.MesProduct;



public interface MesProductCustomerMapper {
	//返回数据库表中数据的条数
	Long getproductCount();
	int countBySearchDto(@Param("dto")SearchProductDto dto);
//分页
	List<MesProduct> getPageListBySearchDto(@Param("dto")SearchProductDto dto,@Param("page")PageQuery page);



	
	
}