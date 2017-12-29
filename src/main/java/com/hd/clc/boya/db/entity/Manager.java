package com.hd.clc.boya.db.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
public class Manager {
    private Integer id;//数据库主键
    private String  account;//账号名
    private String  password;//密码
    private Integer managerType;//管理员类型（0：超级管理员；1：普通管理员）
    private Date addTime;//添加时间
    private Date lastLoginTime;//最后一次登录时间
    private Integer isDeleted;//是否删除（0：否；1：是）
}
