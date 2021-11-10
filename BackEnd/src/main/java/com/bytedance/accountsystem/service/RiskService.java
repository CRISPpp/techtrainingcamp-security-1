package com.bytedance.accountsystem.service;

import com.bytedance.accountsystem.config.Constant;
import com.bytedance.accountsystem.dto.Environment;
import com.bytedance.accountsystem.mapper.RedisRepository;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RiskService {
    private RedisRepository redisRepository = new RedisRepository();

    public int tagAndIsArgumentSafe(String argument) {
        String lastTimeStr = redisRepository.get(Constant.REDIS_RISK_SLIDE_CAPTCHA, argument);
        redisRepository.put(Constant.REDIS_RISK_SLIDE_CAPTCHA, argument, String.valueOf(System.currentTimeMillis()),
                Constant.RISK_SLIDE_CAPTCHA_EMIT_TIME, TimeUnit.MILLISECONDS);
        if (lastTimeStr == null) {
            return 0;
        } else {
            long lastTime = Long.parseLong(lastTimeStr);
            long deltaT = System.currentTimeMillis() - lastTime;
            int increment = deltaT < Constant.RISK_SLIDE_CAPTCHA_EMIT_TIME ? 1 : 0;

            String countString = redisRepository.get(Constant.REDIS_RISK_BLOCK, argument);
            int count;
            if (increment > 0)
                count = countString == null ? increment : Integer.parseInt(countString) + increment;
            else
                count = countString == null ? 0 : Integer.parseInt(countString);
            redisRepository.put(Constant.REDIS_RISK_BLOCK, argument, String.valueOf(count),
                    Constant.RISK_BLOCK_EMIT_TIME, TimeUnit.MINUTES);
            if (count >= Constant.RISK_BLOCK_EMIT_COUNT) return 3;
            else if (count > Constant.RISK_WAIT_EMIT_COUNT) return 2;
            else return increment;
        }
    }

    public int tagAndIsEnvironmentSafe(Environment environment) {
        String deviceId = environment.getDeviceId();
        String ip = environment.getIp();
        //是否有相关参数
        if (deviceId == null || ip == null || "".equals(deviceId) || "".equals(ip)) return -1;
        int needSlideCaptcha = 0;
        needSlideCaptcha = tagAndIsArgumentSafe(deviceId);
        needSlideCaptcha = Math.max(needSlideCaptcha, tagAndIsArgumentSafe(ip));
        return needSlideCaptcha;
    }

    public void saveNeedCaptcha(Environment environment) {
        String deviceId = environment.getDeviceId();
        String ip = environment.getIp();
        redisRepository.put(Constant.REDIS_NEED_CAPTCHA, environment.getDeviceId(), "true", Constant.NEED_CAPTCHA_TIME, TimeUnit.MINUTES);
        redisRepository.put(Constant.REDIS_NEED_CAPTCHA, environment.getIp(), "true", Constant.NEED_CAPTCHA_TIME, TimeUnit.MINUTES);
    }

    public boolean isNeedCaptcha(Environment environment) {
        String deviceId = environment.getDeviceId();
        String ip = environment.getIp();
        String deviceIdBool = redisRepository.get(Constant.REDIS_NEED_CAPTCHA, environment.getDeviceId());
        String ipBool = redisRepository.get(Constant.REDIS_NEED_CAPTCHA, environment.getIp());
        return "true".equals(deviceIdBool) || "true".equals(ipBool);
    }
}
