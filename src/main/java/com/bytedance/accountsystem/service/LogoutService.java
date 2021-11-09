package com.bytedance.accountsystem.service;


import com.bytedance.accountsystem.config.Constant;
import com.bytedance.accountsystem.dto.Environment;
import com.bytedance.accountsystem.mapper.RedisRepository;
import com.bytedance.accountsystem.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class LogoutService {
    @Autowired
    private RedisRepository redisRepository;
    @Autowired
    private UserMapper userMapper;

    public boolean logout(HttpSession session) {
        String sessionId = (String) session.getAttribute(Constant.HTTP_SESSION_ID);
        session.removeAttribute(Constant.HTTP_SESSION_ID);
        return redisRepository.delete(Constant.REDIS_SESSION_ID, sessionId);
    }

    public boolean cancelAccount(HttpSession session) {
        String sessionId = (String) session.getAttribute(Constant.HTTP_SESSION_ID);
        session.removeAttribute(Constant.HTTP_SESSION_ID);
        redisRepository.delete(Constant.REDIS_SESSION_ID, sessionId);
        String username = redisRepository.get(Constant.REDIS_SESSION_ID, sessionId);
        if (username == null) return false;
        return userMapper.deleteByUsername(username) == 1;
    }
}
