package com.bytedance.accountsystem.controller;

import com.bytedance.accountsystem.dto.Environment;
import com.bytedance.accountsystem.dto.RespBean;
import com.bytedance.accountsystem.service.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
//import sun.tools.java.Environment;
@RestController
public class LogoutController {
    @Autowired
    private LogoutService logoutService;
    public RespBean logout(Environment environment, int actionType, String sessionId) {
        if(logoutService.userlogout(environment, actionType, sessionId)){
            if(actionType == 1) return RespBean.ok("登出成功");
            else return RespBean.ok("注销成功");
        }
        else{
            if(actionType == 1) return RespBean.error("登出失败");
            else return RespBean.error("注销失败");
        }
    }

}
