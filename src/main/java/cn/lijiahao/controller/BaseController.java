package cn.lijiahao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.lijiahao.Json.JsonResult;
import cn.lijiahao.constant.JsonMessage;
import cn.lijiahao.po.User;
import cn.lijiahao.service.UserService;

@Controller
public class BaseController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login() {
		return "siginin";
	}
	@RequestMapping(value="/sigin",method=RequestMethod.POST)
	@ResponseBody
	public JsonResult sigin(ModelMap model,String username,String password) {
		JsonResult json = new JsonResult();
		User selectUser = new User();
		selectUser.setUsername(username);
		User user = userService.selectByUser(selectUser);
		if(user==null) {
			json.setMessage(JsonMessage.USERNAME_IS_NOT_EXIST);
			return json;
		}
		json.addDatas("user",user);
		return json;
	}
	
	
}
