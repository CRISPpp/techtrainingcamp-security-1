package com.bytedance.accountsystem.dto;

import lombok.Data;

/*
{
    "data": {
        {给前端返回的主体数据}
    },
    "metaInfo": {
        "msg": {操作结果提示},
        "status": {状态码}
    }
}
*/

@Data //自动补全 getter setter 方法
public class RespBean {//返回对象的标准类
    private Object data;
    private meta metaInfo;

    public RespBean(Object data) {
        this.data = data;
    }

    public RespBean(String msg, int status, Object data) {
        this.data = data;
        this.metaInfo = new meta(msg, status);
    }

    public RespBean() {
    }

    public static RespBean build() {
        return new RespBean();
    }

    public static RespBean ok(String msg) {
        return new RespBean(msg, 200, null);
    }

    public static RespBean ok(String msg, Object obj) {
        return new RespBean(msg, 200, obj);
    }

    public static RespBean created(String msg) {
        return new RespBean(msg, 201, null);
    }

    public static RespBean created(String msg, Object obj) {
        return new RespBean(msg, 201, obj);
    }

    public static RespBean forbidden(String msg) {
        return new RespBean(msg, 403, null);
    }

    public static RespBean forbidden(String msg, Object obj) {
        return new RespBean(msg, 403, obj);
    }

    public static RespBean unprocessable(String msg) {
        return new RespBean(msg, 422, null);
    }

    public static RespBean unprocessable(String msg, Object obj) {
        return new RespBean(msg, 422, obj);
    }

    public static RespBean danger(String msg) {
        return new RespBean(msg, 420, null);
    }

    public static RespBean danger(String msg, Object obj) {
        return new RespBean(msg, 420, obj);
    }

    public static RespBean error(String msg) {
        return new RespBean(msg, 500, null);
    }

    public static RespBean error(String msg, Object obj) {
        return new RespBean(msg, 500, obj);
    }

    public static RespBean get(String msg, int status, Object obj) {
        return new RespBean(msg, status, obj);
    }
}

@Data
class meta {
    private String msg;
    private int status;

    public meta() {
    }

    public meta(String msg, int status) {
        this.msg = msg;
        this.status = status;
    }
}