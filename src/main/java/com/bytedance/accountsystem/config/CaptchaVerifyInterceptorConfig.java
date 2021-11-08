package com.bytedance.accountsystem.config;

import com.bytedance.accountsystem.annotation.CaptchaVerify;
import com.bytedance.accountsystem.annotation.RiskDetect;
import com.bytedance.accountsystem.interceptor.CaptchaVerifyInterceptor;
import com.bytedance.accountsystem.interceptor.RiskDetectInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
public class CaptchaVerifyInterceptorConfig
{
	@Autowired
	private CaptchaVerifyInterceptor advice;

	@Bean
	public DefaultPointcutAdvisor getDefaultPointcutAdvisor2() {
		DefaultPointcutAdvisor pointcutAdvisor = new DefaultPointcutAdvisor();
		pointcutAdvisor.setOrder(2);

		//基于方法注解进行拦截
		AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(null, CaptchaVerify.class);
		pointcutAdvisor.setPointcut(pointcut);
		pointcutAdvisor.setAdvice(advice);

		return pointcutAdvisor;
	}
}
