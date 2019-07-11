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
@RequestMapping("/orderBatch.page")
public String orderBatch() {
	return FPAth+"orderBatch";
}
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
public JsonData searchPage(SearchOrderParam param,PageQuery page) {
	System.out.println(page);
	System.out.println(444);
	PageResult<MesOrder> pr=(PageResult<MesOrder>) orderService.searchPageList(param, page);
	return JsonData.success(pr);
}

}
