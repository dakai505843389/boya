package com.hd.clc.boya.db.entity;

import java.util.Date;

public class ClassType {
    private  Integer id;//数据库主键
    private  String typeName;//课程类型名称
    private  Integer sortNum;//排序
    private  Date    addTime;//增加时间
    private  Date    updateTime;//更新时间
    private  Integer isDeleted;//是否删除（0：未删除；1：已删除）
}
