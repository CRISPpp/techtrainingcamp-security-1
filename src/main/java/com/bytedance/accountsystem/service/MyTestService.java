package com.bytedance.accountsystem.service;

import com.bytedance.accountsystem.entity.MyTest;
import com.bytedance.accountsystem.mapper.MyTestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service// 说明该类是Service
public class MyTestService {
    @Autowired//同自动注入
    private MyTestMapper testMapper;

    public MyTest getMyTestById(Integer id){
        return testMapper.mySelectById(id);//通过手写SQL的方法获取结果
//      return  testMapper.selectById(id);//通过BaseMapper继承的一些方法获取结果（详细有哪些参阅MyBatis Plus文档）
    }
}
