package com.bytedance.accountsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bytedance.accountsystem.entity.MyTest;
import com.bytedance.accountsystem.entity.User;
import org.apache.ibatis.annotations.Select;

public interface LoginMapper extends BaseMapper<MyTest> {//数据访问层接口，继承了BaseMapper中了一些基本方法

    @Select("SELECT password FROM user WHERE username=#{username}")//判断密码是否正确
    String UserLogin(String username);

}
