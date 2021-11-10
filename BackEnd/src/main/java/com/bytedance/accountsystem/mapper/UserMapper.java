package com.bytedance.accountsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bytedance.accountsystem.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * FROM user WHERE username = #{username} AND password = #{password}")
    User selectUserByUsernameAndPassword(String username, String password);

    @Delete("DELETE FROM user WHERE username = #{username}")
    Integer deleteByUsername(String username);

    @Select("SELECT COUNT(*) FROM user WHERE username=#{username}")
    Integer selectCountByUsername(String username);

    @Select("SELECT COUNT(phone_number) FROM user WHERE phone_number=#{phoneNumber}")
    Integer selectCountByPhoneNumber(String phoneNumber);

    @Insert("INSERT INTO user(username,password,phone_number) VALUES (#{username},#{password},#{phoneNumber})")
    Integer insertUser(String username, String password, String phoneNumber);


}
