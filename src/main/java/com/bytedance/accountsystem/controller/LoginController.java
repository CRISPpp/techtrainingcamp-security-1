package com.bytedance.accountsystem.controller;

import com.bytedance.accountsystem.dto.Environment;
import com.bytedance.accountsystem.dto.RespBean;
import com.bytedance.accountsystem.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
//import sun.tools.java.Environment;

@RestController
@RequestMapping("/user")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")    //登录有两种方式
    public RespBean login(Environment environment, @RequestParam String username, @RequestParam String password) {
        Map<String,Object> result;
        try {
            result = loginService.login(username, password);
        } catch (Exception e) {
            return RespBean.unprocessable("登录失败"+e.getMessage());
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