package com.bytedance.accountsystem.interceptor;

import com.bytedance.accountsystem.config.Constant;
import com.bytedance.accountsystem.dto.RespBean;
import com.bytedance.accountsystem.service.RiskService;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RiskDetectInterceptor implements MethodInterceptor {
    private RiskService riskService = new RiskService();

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        Method method = invocation.getMethod();
        Object[] arguments = invocation.getArguments();
        ParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] argumentNames = discoverer.getParameterNames(method);

        //是否有相关参数
        int deviceIdIndex = indexOfValue(argumentNames, "deviceId");
        int ipIndex = indexOfValue(argumentNames, "ip");
        if (deviceIdIndex == -1 || ipIndex == -1) return RespBean.error("参数缺失");

        // 是否安全
        int needSlideCaptcha = 0;
        String deviceId = (String) arguments[deviceIdIndex];
        String ip = (String) arguments[ipIndex];
        needSlideCaptcha = riskService.isArgumentSafe(deviceId);
        needSlideCaptcha = Math.max(needSlideCaptcha, riskService.isArgumentSafe(ip));


        if (needSlideCaptcha == 3) {//禁止访问
            Map<String, Object> map = new HashMap<String, Object>() {
                {
                    put("decisionType", 3);
                }
            };
            return RespBean.forbidden("禁止访问", map);
        }


        // 正常 或 滑块验证
        Object object = invocation.proceed();
        if (needSlideCaptcha > 0 && object != null) {
            RespBean resp = (RespBean) object;
            Map<String, Object> data = (Map<String, Object>) resp.getData();
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
