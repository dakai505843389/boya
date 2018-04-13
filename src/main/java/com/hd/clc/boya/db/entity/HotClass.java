package com.hd.clc.boya.db.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class HotClass {
    private Integer id;//数据库主键
    private Integer classId;//课程id
    private String className;
    private Integer classType;
    private String classRoom;
    private Date classBeginTime;
    private Integer isAllowGroup;//是否允许团购（0：不允许；1：允许；2：团购截止）
    private String classImagePath;
    private Integer teacherType;//教师类型（0：达人；1：机构）
    private Integer sortNum;//排序
    private Integer isTop;//是否置顶（0：否；1：是）
    private Date    topTime;//置顶时间
    private Date    addTime;//增加时间
    private Date    updateTime;//更新时间
    private Integer status;//状态（0：在线；1：暂停；2：过期）
    private Integer isDeleted;//是否删除（0：否；1：是）
}
