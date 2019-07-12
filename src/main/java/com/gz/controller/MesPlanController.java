package com.gz.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gz.beans.PageQuery;
import com.gz.beans.PageResult;
import com.gz.common.JsonData;
import com.gz.model.MesPlan;
import com.gz.param.SearchPlanParam;
import com.gz.service.PlanService;


@Controller
@RequestMapping("/plan")
public class MesPlanController {
	private static String FPATH="plan/";
	@Resource
	private PlanService planService;
	
	@RequestMapping("/plan.page")
	public String planPage() {
		return FPATH+"plan";
	}
	@RequestMapping("/planStarted.page")
	public String planStartedPage() {
		return FPATH+"planStarted";
	}
	
	//批量处理待执行计划启动
		//批量启动处理
		@ResponseBody
		@RequestMapping("/planBatchStart.json")
		public JsonData planBatchStart(String ids) {
			planService.batchStartWithIds(ids);
			return JsonData.success();
		}
	//分页显示
    @RequestMapping("/plan.json")
    @ResponseBody
    public JsonData searchPage(SearchPlanParam param, PageQuery page) {
    	PageResult<MesPlan> pr=(PageResult<MesPlan>) planService.searchPageList(param, page);
    	return JsonData.success(pr);
    }
    
    
    
	
}