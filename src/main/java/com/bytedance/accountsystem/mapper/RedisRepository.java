package com.bytedance.accountsystem.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Component
public class RedisRepository
{
//	@Autowired
//	private RedisTemplate redisTemplate;

	@Autowired
	@Qualifier("redisTemplate")
	private RedisTemplate redis;

	public static RedisTemplate redisTemplate;
	@PostConstruct
	public void getRedisTemplate(){
		redisTemplate=this.redis;
	}

	public boolean put(String type, String key, String value, long timeout, TimeUnit unit) {
		String newKey=type+"$"+key;
		redisTemplate.boundValueOps(newKey).set(value,timeout, unit);
		return true;
	}

	public String get(String type,String key) {
		String newKey=type+"$"+key;
		return (String) redisTemplate.boundValueOps(newKey).get();
	}

	public boolean delete(String type,String key){
		String newKey=type+"$"+key;
		return redisTemplate.delete(newKey);
	}
}
