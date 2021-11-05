package com.bytedance.accountsystem.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class MD5Utils {
    public static String encodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return new String(Base64.getEncoder().encode(MessageDigest.getInstance("MD5").digest(str.getBytes("utf-8"))));
    }
}
