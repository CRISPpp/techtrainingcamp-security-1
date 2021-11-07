package com.bytedance.accountsystem.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KaptchaConfig
{
	@Bean
	public DefaultKaptcha producer() {
		Properties properties = new Properties();
		properties.put("kaptcha.border", "no");
		properties.put("kaptcha.textproducer.font.color", "black");
		properties.put("kaptcha.textproducer.char.space", "10");
		properties.put("kaptcha.textproducer.char.length", "4");
		properties.put("kaptcha.image.width", "150");
//		properties.put("kaptcha.image.height", "35");
		properties.put("kaptcha.textproducer.font.size", "35");
		properties.put("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.ShadowGimpy");

		properties.put("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
		properties.put("kaptcha.textproducer.font.names", "Arial");
		properties.put("kaptcha.textproducer.char.string", "123456789");

		Config config = new Config(properties);
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		defaultKaptcha.setConfig(config);
		return defaultKaptcha;
	}
}
