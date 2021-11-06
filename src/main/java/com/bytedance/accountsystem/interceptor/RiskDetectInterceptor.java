package com.bytedance.accountsystem.interceptor;

import com.bytedance.accountsystem.dto.RespBean;
import com.bytedance.accountsystem.service.RiskService;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class RiskDetectInterceptor implements MethodInterceptor
{
	private RiskService riskService=new RiskService();

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
		System.out.println(deviceIdIndex+" "+ipIndex);
		for (String argumentName : argumentNames) {
			System.out.println(argumentName);
		}
		for (Object argument : arguments) {
			System.out.println(argument);
		}

		// 是否安全
		String deviceId = (String) arguments[deviceIdIndex];
		String ip = (String) arguments[ipIndex];
		if (!riskService.isDeviceIdSafe(deviceId))
			return RespBean.forbidden("禁止访问");

		if (!riskService.isIpSafe(ip))
			return RespBean.forbidden("禁止访问");

		// 正常
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
