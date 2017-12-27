package com.hd.clc.boya.db.entity;

import java.util.Date;

public class UserCollectionTeacherMap {
    private Integer id;//数据库主键
    private Integer userId;//用户ID
    private Integer teacherId;//教师ID
    private Date    addTime;//增加时间
    private Date    updateTime;//更新时间
    private Integer isDeleted;//是否删除（0：否；1：是）
}
