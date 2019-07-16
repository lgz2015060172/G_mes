package com.gz.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gz.service.OrderService;
@Controller
@RequestMapping("/test")
public class TestController {
	@Resource OrderService OrderService;
	@RequestMapping("/test.json")
	public void test() {
	}
}
