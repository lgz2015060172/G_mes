package com.gz.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.gz.beans.PageQuery;
import com.gz.beans.PageResult;
import com.gz.common.JsonData;
import com.gz.model.MesProduct;
import com.gz.param.MesProductVo;
import com.gz.param.SearchProductParam;
import com.gz.service.ProductService;
@SessionAttributes
@Controller
@RequestMapping("/product")
public class MesProductController {
	@Resource
	private ProductService productService;
	private static String FPATH = "product/";

	@RequestMapping("/product.page")
	public String product() {
		return FPATH + "product";
	}
	//////////////////////////////////////
	//更新
	@ResponseBody
	@RequestMapping("/productUpdate.json")
	public JsonData updata(MesProductVo mesProductVo) {
		productService.update(mesProductVo);
		return JsonData.success(true);
	}
	
	
	// 钢锭到库查询页面
	@RequestMapping("/productIron.page")
	public String productIron() {
		return FPATH + "productIron";
	}

	@RequestMapping("/productIron.json")
	@ResponseBody
	// 非钢锭材料到库分页
	public JsonData searchIronPage(SearchProductParam param, PageQuery page) {
		PageResult<MesProduct> pr = (PageResult<MesProduct>) productService.searchIronPageList(param, page);
		return JsonData.success(pr);
	}

	// 非钢锭材料到库查询页面
	@RequestMapping("/productCome.page")
	public String productCome() {
		return FPATH + "productCome";
	}

	@RequestMapping("/productCome.json")
	@ResponseBody
	// 材料到库分页
	public JsonData searchComePage(SearchProductParam param, PageQuery page) {
		PageResult<MesProduct> pr = (PageResult<MesProduct>) productService.searchComePageList(param, page);
		return JsonData.success(pr);
		//PageResult是一个容器 容器里面存的是封装成MesProduct的对象
	}

	// 材料增加页面
	@RequestMapping("/productinsert.page")
	public String productinsert() {
		return FPATH + "productinsert";
	}

	// 产品批量增
	@ResponseBody
	@RequestMapping("/insert.json")
	public JsonData insertAjax(MesProductVo mesProductVo) {
		productService.productBatchInserts(mesProductVo);
		return JsonData.success();
	}

	@RequestMapping("/product.json")
	@ResponseBody
	// 批量增加分页
	public JsonData searchPage(SearchProductParam param, PageQuery page) {
		PageResult<MesProduct> pr = (PageResult<MesProduct>) productService.searchPageList(param, page);
		return JsonData.success(pr);
	}

	// 批量启动处理
	@ResponseBody
	@RequestMapping("/productBatchStart.json")
	public JsonData productBatchStart(String ids) {
		productService.batchStart(ids);
		return JsonData.success();
	}

	// 钢锭批量处理启动
	@ResponseBody
	@RequestMapping("/productBatchIronStart.json")
	public JsonData productBatchIronStart(String ids) {
		productService.batchStart(ids);
		return JsonData.success();
	}
	
	
	
	
	////////////////////////////////////////材料绑定模块productBindList
	@RequestMapping("/productBindList.page")
	public String productBindList() {
		return FPATH+"productBindList";
	}
	
  //材料绑定主页分页显示
	@ResponseBody
	@RequestMapping("/productBindSerach.json")
    public JsonData SearchProductBind(SearchProductParam param,PageQuery page) {
		PageResult<MesProduct> pr=(PageResult<MesProduct>) productService.searchBindPageList(param, page);
		return JsonData.success(pr);
	}
	//材料绑定入口界面productbind.json
	@RequestMapping("/productbind.page")
	public String Productbindpage() {

		return FPATH+"productBind";
	}
	//绑定材料进入绑定环节
	@ResponseBody
	@RequestMapping("/productbind.json")
	public JsonData searchProductBindByPage(String id,HttpSession session) {
		Integer ID=Integer.parseInt(id);
		MesProduct mesProduct=productService.selectOneById(ID);
		if(mesProduct!=null) {
			session.setAttribute("product", mesProduct);
		}
		return JsonData.success(mesProduct);
	}
///查询出可以绑定的子材料
   @ResponseBody
   @RequestMapping("/searchChildBind.json")
   public JsonData searchChildbin(SearchProductParam param ,PageQuery page) {
	     PageResult<MesProduct> pr= (PageResult<MesProduct>) productService.searchChildBindPageList(param, page);
	   return JsonData.success(pr);
   }
   //子材料绑定父级材料
   @ResponseBody
   @RequestMapping("/productbindSecond.json")
   public JsonData productbindSecond( String childId,HttpSession session) {
	   productService.bind(session,childId);
	   return JsonData.success();
   }
	
   //绑定材料的页面显示
   @ResponseBody
   @RequestMapping("/searchBinded.json")
   public JsonData searchBindSecondPageList(SearchProductParam param,PageQuery page) {
	   PageResult<MesProduct> pr= (PageResult<MesProduct>) productService.searchBindSecondPageList(param, page);
	   return JsonData.success(pr);
	  
   }
}
		
