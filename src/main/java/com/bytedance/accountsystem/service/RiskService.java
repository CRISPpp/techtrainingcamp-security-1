package com.bytedance.accountsystem.service;

import com.bytedance.accountsystem.mapper.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RiskService
{
	@Autowired
	private RedisRepository redisRepository;

	public boolean isIpSafe(String ip){
		return true;
	}

	public boolean isDeviceIdSafe(String deviceId){
		return false;
	}
}
