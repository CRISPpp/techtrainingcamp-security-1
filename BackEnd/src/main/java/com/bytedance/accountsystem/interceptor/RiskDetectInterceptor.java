package com.bytedance.accountsystem.interceptor;

import com.bytedance.accountsystem.dto.Environment;
import com.bytedance.accountsystem.dto.RespBean;
import com.bytedance.accountsystem.service.RiskService;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RiskDetectInterceptor implements MethodInterceptor {
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

        //提取参数
        Environment environment = (Environment) arguments[environmentIndex];

        // 是否安全
        int needSlideCaptcha = Math.max(
                riskService.tagAndIsEnvironmentSafe(environment),
                riskService.isNeedCaptcha(environment) ? 1 : 0
        );
        if (needSlideCaptcha > 1) {//禁止访问
            Map<String, Object> map = new HashMap<String, Object>() {
                {
                    put("decisionType", needSlideCaptcha);
                }
            };
            return RespBean.danger(needSlideCaptcha == 2 ? "您操作过于频繁，请稍等后再操作" : "您操作过于频繁，已被禁止访问", map);
        } else if (needSlideCaptcha == 1) {
            riskService.saveNeedCaptcha(environment);
        }

        // 正常 或 滑块验证
        Object object = invocation.proceed();

        if (needSlideCaptcha == 1 && object != null) {
            RespBean resp = (RespBean) object;
            Map<String, Object> data = (Map<String, Object>) resp.getData();
            if (data == null) data = new HashMap<String, Object>();
            data.put("decisionType", needSlideCaptcha);
            resp.setData(data);
            return resp;
        }
        return object;
    }


    private int indexOfValue(String[] array, String value) {
        for (int i = 0; i < array.length; i++) {
            if (value.equals(array[i])) return i;
        }
        return -1;
    }
}
