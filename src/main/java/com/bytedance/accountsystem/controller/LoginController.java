package com.bytedance.accountsystem.controller;

import com.bytedance.accountsystem.annotation.CaptchaVerify;
import com.bytedance.accountsystem.annotation.RiskDetect;
import com.bytedance.accountsystem.config.Constant;
import com.bytedance.accountsystem.dto.Environment;
import com.bytedance.accountsystem.dto.RespBean;
import com.bytedance.accountsystem.dto.Verify;
import com.bytedance.accountsystem.exception.PasswordInvalidException;
import com.bytedance.accountsystem.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
//import sun.tools.java.Environment;

@RestController
@RequestMapping("/user")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @CaptchaVerify
//    @RiskDetect
    @PostMapping("/login")    //登录有两种方式
    public RespBean login(Environment environment,
                          Verify verify,
                          @RequestParam String username,
                          @RequestParam String password,
                          HttpSession session
                          ) {
        Map<String, Object> result;
        try {
            result = loginService.login(username, password,session);
        } catch (PasswordInvalidException e) {
            return RespBean.unprocessable("用户名或密码错误");
        } catch (Exception e) {
            return RespBean.unprocessable("登录失败" + e.getMessage());
        }
        if (result != null) {
            return RespBean.ok("登录成功", result);//返回结果对象
        }
        return RespBean.unprocessable("登录失败");
    }

//    @RequestMapping("/phonelogin")
//    public RespBean phoneLogin(@RequestParam String deviceId, @RequestParam String ip,
//                               @RequestParam String phoneNumber, @RequestParam String verifyCode, @RequestParam String verifyToken) {
//        Login login = loginService.phoneLogin(phoneNumber, verifyCode, verifyToken);
//        if(login != null){
//            return RespBean.ok("登录成功", login);//返回结果对象
//        }else{
//            return RespBean.error("登录失败");
//        }
//    }
}
