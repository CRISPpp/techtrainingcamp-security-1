package com.bytedance.accountsystem.service;

import com.bytedance.accountsystem.config.Constant;
import com.bytedance.accountsystem.exception.PasswordInvalidException;
import com.bytedance.accountsystem.exception.RegisterException;
import com.bytedance.accountsystem.mapper.RedisRepository;
import com.bytedance.accountsystem.mapper.UserMapper;
import com.bytedance.accountsystem.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    @Autowired
    private RedisRepository redisRepository;
    @Autowired
    private UserMapper userMapper;

    public boolean register(String username, String password
            , String phoneNumber) throws RegisterException, UnsupportedEncodingException, NoSuchAlgorithmException {

        /*
         * 正则匹配手机号 密码*/

        if (userMapper.selectCountByUsername(username) != 0) {
            throw new RegisterException("用户名已存在");
        }
        if (userMapper.selectCountByPhoneNumber(phoneNumber) != 0) {
            throw new RegisterException("手机号已被注册");
        }

        String encodePassword = MD5Utils.encodeByMd5(password);
        return userMapper.insertUser(username, encodePassword, phoneNumber) == 1;

    }

    public Map<String, Object> login(String username, String password, HttpSession request) throws UnsupportedEncodingException, NoSuchAlgorithmException, PasswordInvalidException {
        String encodedPassword = MD5Utils.encodeByMd5(password);
        if (userMapper.selectUserByUsernameAndPassword(username, encodedPassword) == null) {
            throw new PasswordInvalidException();
        } else {
            String sessionId = generateSessionId();
            redisRepository.put(Constant.REDIS_SESSION_ID, sessionId, username, Constant.LOGIN_KEEP_TIME, TimeUnit.MINUTES);
            request.setAttribute(Constant.HTTP_SESSION_ID, sessionId);
            return new HashMap<String, Object>() {{
                put("expireTime", Constant.LOGIN_KEEP_TIME);
                put("decisionType", 0);
            }};
        }
    }

//    public Login phoneLogin(String PhoneNumber, String verifyCode, String verifyToken){
//            Login login = new Login();
//            String Code = redisRepository.get("token", "CAPTCHA_TOKEN$" + verifyToken);
//            if(Code != null && Code.equals(verifyCode)) {   //如果用户输入的验证码正确
//                login.setexpireTime(180L);    //设置过期时间
//                String sessionId = generateSessionId();
//                login.setsessionId(sessionId);
//                redisRepository.put("session","sessionId",sessionId,180L, TimeUnit.MINUTES); //向redis中写入sessionId
//                login.setDecisionType(0);
//                return  login;
//             }
//        return null;   //否则返回空
//    }

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



    public static synchronized String generateSessionId() {     //sessionid生成算法
        // Generate a byte array containing a session identifier
        Random random = new SecureRandom();  // 取随机数发生器, 默认是SecureRandom
        byte bytes[] = new byte[16];
        random.nextBytes(bytes); //产生16字节的byte
        bytes = getDigest().digest(bytes); // 取摘要,默认是"MD5"算法
        // Render the result as a String of hexadecimal digits
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {     //转化为16进制字符串
            byte b1 = (byte) ((bytes[i] & 0xf0) >> 4);
            byte b2 = (byte) (bytes[i] & 0x0f);
            if (b1 < 10)
                result.append((char) ('0' + b1));
            else
                result.append((char) ('A' + (b1 - 10)));
            if (b2 < 10)
                result.append((char) ('0' + b2));
            else
                result.append((char) ('A' + (b2 - 10)));
        }
        return (result.toString());
    }

    private static MessageDigest getDigest() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md;
        } catch (NoSuchAlgorithmException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
