package cn.lijiahao.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.lijiahao.email.verificationCode.EmailVerificationCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.lijiahao.Json.JsonResult;
import cn.lijiahao.constant.JsonMessage;
import cn.lijiahao.po.User;
import cn.lijiahao.service.UserService;
import cn.lijiahao.session.SessionManager;
import cn.lijiahao.verificationCode.VerificationCodeService;

/**
 * sign、register、register verification code in this controller
 */
@Controller
public class BaseController {
    @Autowired
    private UserService userService;

    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private VerificationCodeService verificationCodeService;
    @Autowired
    private EmailVerificationCode emailVerificationCode;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "signin";
    }

	@RequestMapping(value = "/sigin")
	@ResponseBody
	public JsonResult sigin(ModelMap model, String username, String password, HttpSession session,
			@CookieValue(value = "sid", defaultValue = "") String sid, HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult json = new JsonResult();
		User selectUser = new User();
//        selectUser.setUsername(username);
		selectUser.setEmail(username);
		User user = userService.selectByUser(selectUser);
		if (user == null) {
			json.setMessage(JsonMessage.USERNAME_IS_NOT_EXIST);
			return json;
		}
//		user.getPassword().equals(Md5Utils.encrypt(password, user.getSalt()))
		if (!user.getPassword().equals(password)) {
			json.setMessage("密码错误");
			json.setStatus(JsonResult.JsonResultEmum.ERROR);
			return json;
		} else {
			boolean isRememberMe = request.getParameter("remember_me") != null;
			if (isRememberMe) {
				Cookie cookie = new Cookie("sid", session.getId());
				cookie.setHttpOnly(true);
				cookie.setMaxAge(60 * 60 * 24 * 5);
				response.addCookie(cookie);
			}
			sessionManager.setLoginInfor(session.getId(), user, isRememberMe);
			json.setMessage("登录成功");
			json.setStatus(JsonResult.JsonResultEmum.SUCCESS);
		}
		json.addDatas("user", user);
		return json;
	}
	
	@RequestMapping(value = "/getSessionMessage")
	@ResponseBody
	public JsonResult getSessionMessage(HttpSession session) {
		JsonResult json = new JsonResult();
		if(sessionManager.getSessionId(session.getId())!=null) {
			json.addDatas("session", sessionManager.getSession(session.getId()));
		} else {
			json.setMessage("please sigin first");
		}
		
		return json;
	}

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        return "register";
    }

    @RequestMapping(value = "/doRegister", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult doRegister(ModelMap model, HttpSession session, String username, String password, String email, String checkCode, String verificationCode,
                                 String passwordConfirm) {
        JsonResult json = new JsonResult();
        User user = new User();
        String sessionId = session.getId();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        if (userService.selectByUser(user) != null) {
            json.setMessage("Username already exists");
            return json;
        }
        if (!user.getPassword().equals(passwordConfirm)) {
            json.setMessage("The password entered twice does not match");
            return json;
        }

        User userEmail = new User();
        userEmail.setEmail(email);
        if (userService.selectByUser(userEmail) != null){
            json.setStatus(JsonResult.JsonResultEmum.ERROR);
            json.setMessage("Email is already in use");
            return json;
        }

        if (!verificationCodeService.verifyCode(sessionId, verificationCode)) {
            json.setMessage("Wrong verification code");
            return json;
        }
        if (!emailVerificationCode.verifyCode(sessionId, checkCode)) {
            json.setMessage("Wrong email verification code");
            return json;
        }
        int id = -1;
        id = userService.add(user);
        if (id != -1) {
            json.setMessage("Register success");
            json.setSuccess(true);
        }
        return json;
    }

    /**
     * 生成并缓存注册页面的验证码
     * 此接口做限流
     *
     * @return
     */
    @RequestMapping(value = "/getRegisterVerificationCode", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getRegisterVerificationCode(HttpSession session) {
        JsonResult json = new JsonResult();
        String sessionId = session.getId();
        String code = verificationCodeService.getVerificationCode(sessionId);
        json.addDatas("verificationCode", code);
        json.setSuccess(true);
        System.out.println(code);
        return json;
    }

    /**
     * this request can do current limiting
     * this method can verify code if is right
     *
     * @param session we can get sessionId from this parameter
     * @param vCode   the code of verification
     * @return
     */
    @RequestMapping(value = "/verifyVerificationCode", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult verifyVerificationCode(HttpSession session, String vCode) {
        JsonResult json = new JsonResult();
        String sessionId = session.getId();
        boolean success = verificationCodeService.verifyCode(sessionId, vCode);
        if (success) {
            json.setSuccess(true);
        } else {
            json.setSuccess(false);
        }
        return json;
    }

    /**
     * 生成并缓存注册页面的手机验证码
     * 此接口做限流
     *
     * @return
     */
    @RequestMapping(value = "/getRegisterCheckCode", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getRegisterCheckCode() {
        JsonResult json = new JsonResult();

        return json;
    }

    /**
     * this request use to generate a email verification code, and send a email
     *
     * @param session
     * @param email
     * @return
     */
    @RequestMapping(value = "/generateEmailCode", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult generateEmailCode(HttpSession session, String email) {
        JsonResult jsonResult = new JsonResult();
        String sessionId = session.getId();
        emailVerificationCode.getEmailVerificationCode(sessionId, email);
        jsonResult.setSuccess(true);
        return jsonResult;
    }

    /**
     * this method use to verify Email Code
     *
     * @param session
     * @param code    verification code
     * @return
     */
    @RequestMapping(value = "/verifyEmailCode", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult verifyEmailCode(HttpSession session, String code) {
        JsonResult jsonResult = new JsonResult();
        String sessionId = session.getId();
        boolean isRight = emailVerificationCode.verifyCode(sessionId, code);
        jsonResult.setSuccess(isRight);
        return jsonResult;
    }

    /**
     * check username if exists
     * @param username
     * @return
     */
    @RequestMapping(value = "/checkUsername", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult checkUsername(String username){
        JsonResult jsonResult = new JsonResult();
        User user = new User();
        user.setUsername(username);
        jsonResult.setSuccess(true);
        User resultUser = userService.selectByUser(user);
        if (resultUser!=null){
            userService.get(resultUser.getId());
            jsonResult.setSuccess(false);
        }
        return jsonResult;
    }


}
