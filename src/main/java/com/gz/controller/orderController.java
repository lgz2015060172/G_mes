package com.gz.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gz.beans.PageQuery;
import com.gz.beans.PageResult;
import com.gz.common.JsonData;
import com.gz.model.MesOrder;
import com.gz.param.MesOrderVo;
import com.gz.param.SearchOrderParam;
import com.gz.service.OrderService;

@Controller
@RequestMapping("/order")
public class orderController {
	 
@Resource
private OrderService orderService;
private static String FPAth="order/";
@RequestMapping("/order.page")
public String orderPage() {
	return FPAth+"order";
}
@RequestMapping("/orderBatch.page")
public String orderBatch() {
	return FPAth+"orderBatch";
}
//批量增加
@ResponseBody
@RequestMapping("/insert.json")
public JsonData insertAjax(MesOrderVo MesOrderVo) {
 orderService.orderBatchInserts(MesOrderVo);
	return JsonData.success();
}
/*@RequestMapping("/update.json")
@ResponseBody
public JsonData updateOrder(MesOrderVo mesOrderVo) {
	orderService.update(mesOrderVo);
	return JsonData.success();
}*/

@RequestMapping("/order.json")
@ResponseBody
//分页
public JsonData searchPage(SearchOrderParam param,PageQuery page) {
	PageResult<MesOrder> pr=(PageResult<MesOrder>) orderService.searchPageList(param, page);
	return JsonData.success(pr);
}
//批量启动处理
	@ResponseBody
	@RequestMapping("/orderBatchStart.json")
	public JsonData orderBatchStart(String ids) {
		orderService.batchStart(ids);
		return JsonData.success();
	}

}
