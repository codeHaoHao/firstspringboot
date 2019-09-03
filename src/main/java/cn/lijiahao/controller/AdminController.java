package cn.lijiahao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@RequestMapping("/index")
	public String admin() {
		return "admin/index";
	}
	
	@RequestMapping("/tables")
	public String tables() {
		return "admin/tables";
	}
}
