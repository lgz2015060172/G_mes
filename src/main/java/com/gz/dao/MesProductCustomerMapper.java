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
	//查询的数据库数据key=dto value = dto，Mapper.xml中的#{}中的参数则是根据@Param括号中的参数来获取相应的数据
	int countBySearchDto(@Param("dto")SearchProductDto dto);
	int countBySearchBindDto(@Param("dto")SearchProductDto dto);
	//自义定的查询方法，从数据库中查询出绑定材料的pid不为空的材料个数
	int countBySearchBindSecondDto(@Param("dto")SearchProductDto dto);
	//这个方法是查询 需要绑定的子材料的真实重量为0
	int countBySearchBindChildDto(@Param("dto")SearchProductDto dto);
    //分页
	List<MesProduct> getPageListBySearchDto(@Param("dto")SearchProductDto dto,@Param("page")PageQuery page);
	//批量启动
	void batchStart(@Param("list")String[] idArray);
	//钢锭查询
	List<MesProduct> getPageListBySearchIronDto(@Param("dto")SearchProductDto dto,@Param("page")PageQuery page);
   //材料绑定首页的数据查询
	List<MesProduct> getPageListBySearchBindDto(@Param("dto")SearchProductDto dto,@Param("page")PageQuery page);
	//材料绑定的分页 查询出可以绑定的子材料
	List<MesProduct> getPageListBySearchBindChildDto(@Param("dto")SearchProductDto dto,@Param("page")PageQuery page);
	//getPageListBySearchSecondBindDto
	List<MesProduct> getPageListBySearchSecondBindDto(@Param("dto")SearchProductDto dto);
}