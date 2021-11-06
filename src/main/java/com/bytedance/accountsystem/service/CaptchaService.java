package com.bytedance.accountsystem.service;

import com.bytedance.accountsystem.config.Constant;
import com.bytedance.accountsystem.mapper.RedisRepository;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class CaptchaService
{
	@Autowired
	private DefaultKaptcha producer;

	@Autowired
	private RedisRepository redisRepository;

	public Map<String, Object> generateCaptcha() throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		String captcha = producer.createText();

		BufferedImage image = producer.createImage(captcha);
		ImageIO.write(image, "png", out);
		String base64bytes = Base64.getEncoder().encodeToString(out.toByteArray());

		String verifyCodeSrc = "data:image/png;base64," + base64bytes;
		String verifyToken = UUID.randomUUID().toString();
		redisRepository.put(Constant.REDIS_CAPTCHA_TOKEN, verifyToken, captcha,
				Constant.CAPTCHA_EXPIRE_TIME, TimeUnit.MINUTES);

		Map<String, Object> map = new HashMap<String, Object>()
		{
			{
				put("decisionType", 0);
				put("verifyToken", verifyToken);
				put("verifyImg", verifyCodeSrc);
				put("expireTime", Constant.CAPTCHA_EXPIRE_TIME);
			}
		};
		return map;
	}
}
