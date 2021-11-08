package com.bytedance.accountsystem.config;

import com.bytedance.accountsystem.interceptor.CaptchaVerifyInterceptor;
import com.bytedance.accountsystem.interceptor.RiskDetectInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorBeanCreateConfig{
    @Bean
    public CaptchaVerifyInterceptor getCaptchaVerifyInterceptor(){
        return new CaptchaVerifyInterceptor();
    }

    @Bean
    public RiskDetectInterceptor getRiskDetectInterceptor(){
        return new RiskDetectInterceptor();
    }
}
