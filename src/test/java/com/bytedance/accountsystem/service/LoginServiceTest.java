package com.bytedance.accountsystem.service;

import com.bytedance.accountsystem.config.Constant;
import com.bytedance.accountsystem.exception.PasswordInvalidException;
import com.bytedance.accountsystem.mapper.RedisRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoginServiceTest {
    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisRepository redisRepository;

    @Test
    public void test1() throws UnsupportedEncodingException, NoSuchAlgorithmException, PasswordInvalidException {
        Map<String,Object> map= loginService.login("test","test");
        System.out.println(map);
        System.out.println(redisRepository.get(Constant.REDIS_SESSION_ID, (String) map.get("sessionId")));
    }
}