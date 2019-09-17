package cn.lijiahao.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.lijiahao.Json.JsonResult;
import cn.lijiahao.constant.JsonMessage;
import cn.lijiahao.md5.Md5Utils;
import cn.lijiahao.po.User;
import cn.lijiahao.service.UserService;
import cn.lijiahao.session.SessionManager;

@Controller
public class BaseController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login() {
		return "signin";
	}
	@RequestMapping(value="/sigin",method=RequestMethod.POST)
	public void sigin(ModelMap model,String username,String password,HttpSession session) {
		JsonResult json = new JsonResult();
		User selectUser = new User();
		selectUser.setUsername(username);
		User user = userService.selectByUser(selectUser);
		if(user==null) {
			json.setMessage(JsonMessage.USERNAME_IS_NOT_EXIST);
//			return "/login";
		}
//		user.getPassword().equals(Md5Utils.encrypt(password, user.getSalt()))
		if(user.getPassword().equals(user.getPassword())) {
			json.setMessage("登录成功");
			json.setSuccess(true);
			SessionManager.setSessionId(session.getId());
//			return "/admin/index";
		}
		json.addDatas("user",user);
//		return "/admin/index";
	}
	
	
}
