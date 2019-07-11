package com.gz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/test")
public class TestController {
	@RequestMapping("/test.json")
	public void test() {
		System.out.println(666);
	}
}
