package com.bytedance.accountsystem.config;

import com.bytedance.accountsystem.annotation.RiskDetect;
import com.bytedance.accountsystem.interceptor.RiskDetectInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class RiskDetectInterceptorConfig
{
	@Bean
	public DefaultPointcutAdvisor getDefaultPointcutAdvisor() {
		DefaultPointcutAdvisor pointcutAdvisor = new DefaultPointcutAdvisor();
		pointcutAdvisor.setOrder(Ordered.HIGHEST_PRECEDENCE + 500);

		//基于方法注解进行拦截
		AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(null, RiskDetect.class);
		RiskDetectInterceptor advice = new RiskDetectInterceptor();
		pointcutAdvisor.setPointcut(pointcut);
		pointcutAdvisor.setAdvice(advice);

		return pointcutAdvisor;
	}
}
