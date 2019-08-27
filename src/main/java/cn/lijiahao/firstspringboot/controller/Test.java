package cn.lijiahao.firstspringboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Test {
	
	@RequestMapping("/index")
	public String index() {
		
		return "index";
	}
	@RequestMapping("/sigin")
	public String sigin() {
		
		return "signin";
	}
	@RequestMapping("/register")
	public String register() {
		
		return "register";
	}
}
