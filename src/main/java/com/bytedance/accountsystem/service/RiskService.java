package com.bytedance.accountsystem.service;

import com.bytedance.accountsystem.config.Constant;
import com.bytedance.accountsystem.mapper.RedisRepository;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RiskService {
    private RedisRepository redisRepository = new RedisRepository();

    public int isArgumentSafe(String argument) {
        String lastTime = redisRepository.get(Constant.REDIS_RISK_SLIDE_CAPTCHA, argument);
        redisRepository.put(Constant.REDIS_RISK_SLIDE_CAPTCHA, argument, String.valueOf(System.currentTimeMillis()),
                Constant.RISK_SLIDE_CAPTCHA_EMIT_TIME, TimeUnit.MILLISECONDS);
        if (lastTime == null) {
            return 0;
        } else {
            String countString = redisRepository.get(Constant.REDIS_RISK_BLOCK, argument);
            int count = countString == null ? 1 : Integer.parseInt(countString) + 1;
            redisRepository.put(Constant.REDIS_RISK_BLOCK, argument, String.valueOf(count),
                    Constant.RISK_BLOCK_EMIT_TIME, TimeUnit.MINUTES);

            if (count >= Constant.RISK_BLOCK_EMIT_COUNT) return 3;
            else if (count > Constant.RISK_WAIT_EMIT_COUNT) return 2;
            else return 1;
        }
    }
}
