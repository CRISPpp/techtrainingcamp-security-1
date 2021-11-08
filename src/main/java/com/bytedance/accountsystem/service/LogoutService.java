package com.bytedance.accountsystem.service;


import com.bytedance.accountsystem.config.Constant;
import com.bytedance.accountsystem.dto.Environment;
import com.bytedance.accountsystem.mapper.RedisRepository;
import com.bytedance.accountsystem.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogoutService {
    @Autowired
    private RedisRepository redisRepository;
    @Autowired
    private UserMapper userMapper;
    public boolean userlogout(Environment environment, int actionType, String sessionId) {
        String userName = redisRepository.get(Constant.REDIS_SESSION_ID, sessionId);
        //TODO
        if(userName == null) return false; //找不到返回false;
        //登出
        if(actionType == 1){
            redisRepository.redisTemplate.delete(userName);
        }
        //注销
        else{
            redisRepository.redisTemplate.delete(userName);
            userMapper.deleteByUsername(userName);
        }
        return true;
    }
}
