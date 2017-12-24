package com.hd.clc.boya.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserCode {
    private String openid; //用户唯一标识
    private String session_key; //会话密钥
    private String unionid; //用户在开发平台的唯一标识符
}
