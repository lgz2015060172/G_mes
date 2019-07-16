package com.gz.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gz.beans.PageQuery;
import com.gz.beans.PageResult;
import com.gz.common.JsonData;
import com.gz.model.MesOrder;
import com.gz.model.MesProduct;
import com.gz.param.MesProductVo;
import com.gz.param.SearchOrderParam;
import com.gz.param.SearchProductParam;
import com.gz.service.ProductService;

@Controller
@RequestMapping("/product")
public class MesProductController {
	@Resource
	private ProductService productService;
 private static String FPATH="product/";
	@RequestMapping("/product.page")
   public String product() {
	 return FPATH+"product";
 }
	@RequestMapping("/productinsert.page")
	public String productinsert() {
		return FPATH+"productinsert";
	}
	//产品批量增
	@ResponseBody
	@RequestMapping("/insert.json")
	public JsonData insertAjax(MesProductVo mesProductVo) {
	    productService.productBatchInserts(mesProductVo);
		return JsonData.success();
	}
	@RequestMapping("/product.json")
	@ResponseBody
	//分页
	public JsonData searchPage(SearchProductParam param,PageQuery page) {
		PageResult<MesProduct> pr=(PageResult<MesProduct>) productService.searchPageList(param, page);
		return JsonData.success(pr);
	}
	
}
