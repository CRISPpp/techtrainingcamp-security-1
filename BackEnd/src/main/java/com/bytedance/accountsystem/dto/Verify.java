package com.bytedance.accountsystem.dto;

import lombok.Data;

@Data
public class Verify {
    private String verifyCode;
    private String verifyToken;
}
