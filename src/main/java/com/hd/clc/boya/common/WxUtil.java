package com.hd.clc.boya.common;

import com.alibaba.fastjson.JSONObject;
import com.hd.clc.boya.vo.UserCode;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class WxUtil {

    private static String appid = "wxfe0d2b4095047837";
    private static String secret = "99895fbe4170ed1f03be2adb72ecef12";
    private static String grant_type = "authorization_code";
    private static String URL = "https://api.weixin.qq.com/sns/jscode2session?";

    public static UserCode getUserCode(String code){
        String finalUrl = URL + "appid=" + appid + "&secret=" + secret
                + "&js_code=" + code + "&grant_type=" + grant_type;
        try {
            String result = doGet(finalUrl);
            JSONObject resJsonObject = JSONObject.parseObject(result);
            UserCode userCode = (UserCode) JSONObject.toJavaObject(resJsonObject, UserCode.class);
            return userCode;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // 与微信服务器交互
    private static String doGet(String url) throws Exception{
        java.net.URL localUrl = new URL(url);
        // 打开此URL的连接，并返回一个URLConnection对象，它表示到URL所引用的远程对象的连接
        URLConnection connection = localUrl.openConnection();
        // 将URLConnection连接转为HttpURLConnetion连接
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
        // 设置HttpURLConnection参数
        httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
        httpURLConnection.setRequestProperty("Content-Type", "application/text");

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;

        // 若响应码大于300，则表示请求失败，抛出异常
        if (httpURLConnection.getResponseCode() > 300){
            throw new Exception("连接微信服务器失败，错误代码："+httpURLConnection.getResponseCode());
        }
        try {
            // 打开的连接读取的输入流
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            // 写入输入流
            while((tempLine = reader.readLine()) != null){
                resultBuffer.append(tempLine);
            }
        } finally {
            if (reader != null){
                reader.close();
            }
            if (inputStreamReader != null){
                inputStreamReader.close();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        // 返回该URL指定的资源
        return resultBuffer.toString();
    }

}
