package com.bytedance.accountsystem.config;

public class Constant {
    //---HttpSession常量---
    public static String HTTP_SESSION_ID = "sessionId";

    // ---Redis Type常量---
    public static String REDIS_CAPTCHA_TOKEN = "CAPTCHA_TOKEN";
    public static String REDIS_RISK_SLIDE_CAPTCHA = "REDIS_RISK_SLIDE_CAPTCHA";
    public static String REDIS_RISK_WAIT = "REDIS_RISK_WAIT";
    public static String REDIS_RISK_BLOCK = "REDIS_RISK_BLOCK";
    public static String REDIS_SESSION_ID = "REDIS_SESSION_ID";
    public static String REDIS_NEED_CAPTCHA = "REDIS_NEED_CAPTCHA";

    public static Long NEED_CAPTCHA_TIME = 10L;//风险监测需要验证码的持续时间(分钟)
    public static Long CAPTCHA_EXPIRE_TIME = 10L;//验证码过期时间(分钟)
    public static Long RISK_SLIDE_CAPTCHA_EMIT_TIME = 500L;//图形验证触发时间(毫秒)
    public static Long RISK_BLOCK_EMIT_TIME = 3L;//高级风险触发周期(分钟)
    public static int RISK_BLOCK_EMIT_COUNT = 10;//高级风险触发周期内一级验证触发次数
    public static int RISK_WAIT_EMIT_COUNT = 3;//高级风险触发周期内一级验证触发次数

    public static Long LOGIN_KEEP_TIME = 180L;//登录保持时长(分钟)

}
