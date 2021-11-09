package com.bytedance.accountsystem.controller;

import com.bytedance.accountsystem.dto.Environment;
import com.bytedance.accountsystem.dto.RespBean;
import com.bytedance.accountsystem.dto.Verify;
import com.bytedance.accountsystem.exception.PasswordInvalidException;
import com.bytedance.accountsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/register")
    public RespBean register(Environment environment, Verify verify,
                             @RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String phoneNumber) {
        boolean result;
        try {
            result = userService.register(username, password, phoneNumber);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.unprocessable("注册失败：" + e.getMessage());
        }
        if (result) return RespBean.ok("注册成功");
        return RespBean.unprocessable("注册失败");
    }

    //    @CaptchaVerify
    //    @RiskDetect
    @PostMapping("/login")
    public RespBean login(Environment environment, Verify verify,
                          @RequestParam String username,
                          @RequestParam String password,
                          HttpSession session) {
        Map<String, Object> result;
        try {
            result = userService.login(username, password, session);
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

    @PutMapping("/logout")
    public RespBean logout(HttpSession session) {
        try {
            userService.logout(session);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.ok("登出成功");

    }

    @PutMapping
    public RespBean cancelAccount(HttpSession session) {
        try {
            userService.cancelAccount(session);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.ok("注销成功");
    }
}
