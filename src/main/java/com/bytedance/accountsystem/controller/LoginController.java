package com.bytedance.accountsystem.controller;

import com.bytedance.accountsystem.dto.RespBean;
import com.bytedance.accountsystem.entity.Login;
import com.bytedance.accountsystem.mapper.RedisRepository;
import com.bytedance.accountsystem.service.LoginService;
import com.bytedance.accountsystem.service.MyTestService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import sun.tools.java.Environment;

@RestController
@RequestMapping("/user")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping("/usernamelogin")    //登录有两种方式
    public RespBean usernameLogin(@RequestParam String deviceId, @RequestParam String ip,
                                  @RequestParam String username, @RequestParam String password) {
        Login login = loginService.usernameLogin(username, password);
        if(login != null){
            return RespBean.ok("登录成功", login);//返回结果对象
        }else{
            return RespBean.error("登录失败");
        }
    }

    @RequestMapping("/phonelogin")
    public RespBean phoneLogin(@RequestParam String deviceId, @RequestParam String ip,
                               @RequestParam String phoneNumber, @RequestParam String verifyCode, @RequestParam String verifyToken) {
        Login login = loginService.phoneLogin(phoneNumber, verifyCode, verifyToken);
        if(login != null){
            return RespBean.ok("登录成功", login);//返回结果对象
        }else{
            return RespBean.error("登录失败");
        }
    }






}
