package com.hd.clc.boya.db.entity;

import java.util.Date;

public class HotClass {
    private Integer id;//数据库主键
    private Integer classId;//课程id
    private Integer sortNum;//排序
    private Integer isTop;//是否置顶（0：否；1：是）
    private Date    topTime;//置顶时间
    private Date    addTime;//增加时间
    private Date    updateTime;//更新时间
    private Integer status;//状态（0：在线；1：暂停；2：过期）
    private Integer isDeleted;//是否删除（0：否；1：是）
}
