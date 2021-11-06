package com.bytedance.accountsystem.controller;

import com.bytedance.accountsystem.dto.RespBean;
import com.bytedance.accountsystem.service.CaptchaService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Base64;
import java.util.UUID;

@RestController
public class CaptchaController
{
	@Autowired
	private CaptchaService captchaService;

	@RequestMapping("/captcha")
	public RespBean getCaptcha() throws IOException {
		Map<String, Object> result ;
		try {
			result=captchaService.generateCaptcha();
		}catch (Exception e){
			e.printStackTrace();
			return RespBean.unprocessable("请求失败"+e.getMessage());
		}
		return RespBean.ok("请求成功",result);
	}
}
