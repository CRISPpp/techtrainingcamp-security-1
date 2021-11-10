package com.bytedance.accountsystem.mapper;

import com.bytedance.accountsystem.utils.MD5Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class RedisRepositoryTest
{
	@Autowired
	private  RedisRepository redisRepository;

	@Test
	public void test1() throws InterruptedException {
		redisRepository.put("ID","username","a",2,TimeUnit.SECONDS);
		System.out.println(redisRepository.get("ID", "username"));
		Thread.sleep(2500);
		System.out.println(redisRepository.get("ID", "username"));
	}

	@Test
	public void test2() throws UnsupportedEncodingException, NoSuchAlgorithmException {
		System.out.println(MD5Utils.encodeByMd5("test"));
	}
}