package com.bytedance.accountsystem.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisRepository
{
	@Autowired
	private RedisTemplate redisTemplate;

	public boolean insert(String type,String key,String value,long timeout, TimeUnit unit) {
		String newKey=type+"$"+key;
		redisTemplate.boundValueOps(newKey).set(value,timeout, unit);
		return true;
	}

	public String get(String type,String key) {
		String newKey=type+"$"+key;
		return (String) redisTemplate.boundValueOps(newKey).get();
	}

}
