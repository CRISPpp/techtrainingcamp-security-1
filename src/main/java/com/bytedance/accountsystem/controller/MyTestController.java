package com.bytedance.accountsystem.controller;

import com.bytedance.accountsystem.dto.RespBean;
import com.bytedance.accountsystem.mapper.RedisRepository;
import com.bytedance.accountsystem.service.MyTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 相当于@Restful + @Controller 前者是返回的对象自动转成Json字符串，后者是说明该类是Controller
@RequestMapping("/test")// 访问这个类中的方法的uri前缀（在前）
public class MyTestController {
    @Autowired//自动注入对象，不用手动new
    private MyTestService myTestService;

    @RequestMapping("/hello")// 访问这个方法的uri前缀（在后）
    public RespBean test() {//访问该方法的uri是 /test/hello
        return RespBean.ok("查询成功", myTestService.getMyTestById(1));//返回结果对象
    }
}
