package com.hd.clc.boya.common;


import java.util.Map;

public class Result {
    public int code;
    public String msg;

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result() {
        code = 0;
        msg = "请求成功";
    }

}
