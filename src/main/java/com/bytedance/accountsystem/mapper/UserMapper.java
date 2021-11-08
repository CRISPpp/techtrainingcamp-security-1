package com.bytedance.accountsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bytedance.accountsystem.entity.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * FROM user WHERE username = #{username} AND password = #{password}")
    public User selectUserByUsernameAndPassword(String username,String password);
}
