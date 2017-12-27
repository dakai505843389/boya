package com.hd.clc.boya.db.entity;

import java.util.Date;

public class ClassRoom {
    private  Integer id;//数据库主键值
    private  String  classRoom;//教室
    private  Integer maxNumber;//人数上限
    private Date addTime;//增加时间
    private  Date    updateTime;//更新时间
    private  Integer status;//教室状态（0：正常；1：停用）
    private  Integer isDeleted;//是否删除（0：未删除；1：已删除）
}
