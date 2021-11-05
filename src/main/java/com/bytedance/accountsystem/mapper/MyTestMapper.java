package com.bytedance.accountsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bytedance.accountsystem.entity.MyTest;
import org.apache.ibatis.annotations.Select;

public interface MyTestMapper extends BaseMapper<MyTest> {//数据访问层接口，继承了BaseMapper中了一些基本方法

    @Select("SELECT * FROM my_test WHERE id=#{id}")//手写SQL, #{id}是方法参数列表中变量id的对应值
    MyTest mySelectById(Integer id);
}
