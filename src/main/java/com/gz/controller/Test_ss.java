package com.gz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/test")
public class Test_ss {
	
	@RequestMapping("/testMethod")
	public String TestMethod(Model model) {
		String msg="hello";
		model.addAttribute("msg",msg);
		return "test";
		
		
	
}
}