package com.bytedance.accountsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@Data
public class Login
{
    private String sessionId;
    private Long expireTime;
    private Integer decisionType;

    public void setsessionId(String sessionId){
        this.sessionId = sessionId;
    }
    public void setexpireTime(Long expireTime){
        this.expireTime = expireTime;
    }
    public void setDecisionType(Integer decisionType){
        this.decisionType = decisionType;
    }
}

