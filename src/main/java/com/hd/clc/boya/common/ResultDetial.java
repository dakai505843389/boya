package com.hd.clc.boya.common;

public class ResultDetial<T> extends Result {

    public T data;

    public ResultDetial(T data){
        super(0, "请求成功");
        this.data = data;
    }

    /**
     * 接口调用成功的返回结果
     * @param msg
     * @param data
     */
    public ResultDetial(String msg, T data){
        super(0, msg);
        this.data = data;
    }

    /**
     * 接口调用失败的返回结果
     * @param code
     * @param msg
     * @param data
     */
    public ResultDetial(int code, String msg, T data){
        super(code, msg);
        this.data = data;
    }

}
