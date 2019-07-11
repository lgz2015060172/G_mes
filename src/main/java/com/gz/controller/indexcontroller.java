package com.gz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class indexcontroller {
 @RequestMapping("/admin.page")
 public String admin() {
	 return "admin";
 }
 
 
}
