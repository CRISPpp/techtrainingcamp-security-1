package com.bytedance.accountsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data //自动补全 getter setter 方法
public class MyTest {//实体对象，映射到数据库表 （类名和类中变量名是驼峰，对应数据库是下划线）
    @TableId(type = IdType.AUTO) //主键 且AUTO自增
    private Integer id;
    private String msg;
}
