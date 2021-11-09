package com.bytedance.accountsystem.controller;

import com.bytedance.accountsystem.dto.Environment;
import com.bytedance.accountsystem.dto.RespBean;
import com.bytedance.accountsystem.service.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//import sun.tools.java.Environment;
@RestController
@RequestMapping("/user")
public class LogoutController {
    @Autowired
    private LogoutService logoutService;

    @PutMapping("/logout")
    public RespBean logout(Environment environment, HttpSession session) {
        try {
            logoutService.logout(session);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.ok("登出成功");

    }

    @PutMapping
    public RespBean cancelAccount(Environment environment,  HttpSession session) {
        try {
            logoutService.cancelAccount(session);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.ok("注销成功");
    }
}
