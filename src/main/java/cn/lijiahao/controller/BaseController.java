package cn.lijiahao.controller;

import javax.servlet.http.HttpSession;

import cn.lijiahao.email.verificationCode.EmailVerificationCode;
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

    @RequestMapping(value = "/sigin", method = RequestMethod.POST)
    public void sigin(ModelMap model, String username, String password, HttpSession session) {
        JsonResult json = new JsonResult();
        User selectUser = new User();
        selectUser.setUsername(username);
        User user = userService.selectByUser(selectUser);
        if (user == null) {
            json.setMessage(JsonMessage.USERNAME_IS_NOT_EXIST);
//			return "/login";
        }
//		user.getPassword().equals(Md5Utils.encrypt(password, user.getSalt()))
        if (user.getPassword().equals(user.getPassword())) {
            json.setMessage("登录成功");
            json.setSuccess(true);
            sessionManager.setSessionId(session.getId());//登录成功设置sessionId
//			return "/admin/index";
        }
        json.addDatas("user", user);
//		return "/admin/index";
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


}
