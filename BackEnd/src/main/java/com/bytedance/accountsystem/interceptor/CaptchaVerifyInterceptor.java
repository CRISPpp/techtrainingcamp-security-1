package com.bytedance.accountsystem.interceptor;

import com.bytedance.accountsystem.dto.Environment;
import com.bytedance.accountsystem.dto.RespBean;
import com.bytedance.accountsystem.dto.Verify;
import com.bytedance.accountsystem.service.CaptchaService;
import com.bytedance.accountsystem.service.RiskService;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;

public class CaptchaVerifyInterceptor implements MethodInterceptor {
    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private RiskService riskService;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Object[] arguments = invocation.getArguments();
        ParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] argumentNames = discoverer.getParameterNames(method);

        //是否有environment参数
        int environmentIndex = indexOfValue(argumentNames, "environment");
        if (environmentIndex == -1) return RespBean.error("参数缺失");
        Environment environment = (Environment) arguments[environmentIndex];

        if (riskService.isNeedCaptcha(environment)) {
            int verifyIndex = indexOfValue(argumentNames, "verify");
            if (verifyIndex == -1) return RespBean.error("参数缺失");

            //提取参数
            Verify verify = (Verify) arguments[verifyIndex];
            String verifyCode = verify.getVerifyCode();
            String verifyToken = verify.getVerifyToken();

            //验证是否有相关参数
            if (!captchaService.verifyCaptcha(verifyCode, verifyToken)) {
                return RespBean.unprocessable("验证码错误或过期");
            }
        }

        Object object = invocation.proceed();
        return object;
    }

    private int indexOfValue(String[] array, String value) {
        for (int i = 0; i < array.length; i++) {
            if (value.equals(array[i])) return i;
        }
        return -1;
    }
}
