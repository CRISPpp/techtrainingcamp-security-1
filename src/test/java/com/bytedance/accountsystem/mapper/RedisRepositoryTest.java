package com.bytedance.accountsystem.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisRepositoryTest
{
	@Autowired
	private  RedisRepository redisRepository;

	@Test
	public void test1() throws InterruptedException {
		redisRepository.insert("ID","username","a",2,TimeUnit.SECONDS);
		System.out.println(redisRepository.get("ID", "username"));
		Thread.sleep(2500);
		System.out.println(redisRepository.get("ID", "username"));
	}
}