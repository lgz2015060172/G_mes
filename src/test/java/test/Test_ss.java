package test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


public class Test_ss {

@Controller
@RequestMapping("/test")

public class TestStuController {
	
	@RequestMapping("/testMethod")
	public String TestMethod(Model model) {
		String msg="hello";
		model.addAttribute("msg",msg);
		return "test";
		
		
	}
}
}