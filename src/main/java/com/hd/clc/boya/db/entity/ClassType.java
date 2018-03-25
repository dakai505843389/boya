package com.hd.clc.boya.db.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ClassType {
    private  Integer id;//数据库主键
    private  String typeName;//课程类型名称
    private String imagePath;
    private  Integer sortNum;//排序
    private  Date    addTime;//增加时间
    private  Date    updateTime;//更新时间
    private  Integer isDeleted;//是否删除（0：未删除；1：已删除）
}
