package com.hd.clc.boya.db.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class User {
    private Integer id; // 数据库主键
    private String openid; //小程序相对于此用户的唯一标识
    private String unionid; //用户在开放平台唯一标识
    private String userName; //用户名
    private String imagePath; //用户头像图片路径
    private Integer sex; //性别（0：女；1：男；2：保密）
    private String tel; //手机号
    private Integer accountBalance;//账户余额
    private Date addTime; //添加时间
    private Date updateTime; //更新时间
    private Integer loginTimes; // 登录次数
    private Date lastLoginTime; //最后登录时间
    private Integer userType; //用户类型（0：学生；1：教师2：教师资格审核中）
}
