package com.bytedance.accountsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class User
{
	@TableId(type = IdType.AUTO) //主键 且AUTO自增
	private Integer id;
	private String username;
	private String password;
	private String phoneNumber;
}
