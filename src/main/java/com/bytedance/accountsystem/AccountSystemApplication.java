package com.bytedance.accountsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.bytedance.accountsystem.mapper")
public class AccountSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountSystemApplication.class, args);
    }

}
