package com.hd.clc.boya.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccessToken {
    private String access_token;
    private Integer expires_in;
}
