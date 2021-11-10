package com.bytedance.accountsystem.controller;

import com.bytedance.accountsystem.annotation.RiskDetect;
import com.bytedance.accountsystem.dto.Environment;
import com.bytedance.accountsystem.dto.RespBean;
import com.bytedance.accountsystem.service.CaptchaService;
import com.bytedance.accountsystem.service.RiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
public class CaptchaController
{
	@Autowired
	private CaptchaService captchaService;

	@Autowired
	private RiskService riskService;

	@RiskDetect
	@PostMapping("/captcha") //Post形式的请求
	public RespBean getCaptcha(Environment environment) throws IOException {
		Map<String, Object> result ;
		try {
			result=captchaService.generateCaptcha();
		}catch (Exception e){
			e.printStackTrace();
			return RespBean.unprocessable("请求失败"+e.getMessage());
		}
		return RespBean.ok("请求成功",result);
	}

	@GetMapping("/captcha/need")
	public RespBean isNeedCaptcha(Environment environment) {
		boolean result1= riskService.isNeedCaptcha(environment);
		if(result1)	return RespBean.danger("您此次操作需要填写数字验证码");
		int result= riskService.tagAndIsEnvironmentSafe(environment);
		switch (result){
			case -1:
				return RespBean.error("参数缺失");
			case 0:
				return RespBean.ok("不需要验证码");
			case 1:
				return RespBean.danger("您此次操作需要填写数字验证码");
			case 2:
				return RespBean.danger("您操作过于频繁，请稍等后再操作");
			case 3:
				return RespBean.danger("您操作过于频繁，已被禁止访问");
		}
		return RespBean.unprocessable("系统异常");
	}
}
