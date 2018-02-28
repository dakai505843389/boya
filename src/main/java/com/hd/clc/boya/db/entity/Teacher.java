package com.hd.clc.boya.db.entity;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Setter
@Getter
public class Teacher {
    private Integer id;//数据库主键
    private Integer userId;//用户Id
    private String  description;//介绍
    private Integer score;//总评分
    private Integer scoreTimes;//评价次数
    private Integer fansNum;//粉丝数
    private Integer teacherType;//教师类型（0：达人；1：机构）
    private Integer status; //状态（0：资格审核中；1：正常；2：暂停）
    private Date addTime;//添加时间
    private Date updateTime;//更新时间
    private Integer isDeleted;//是否删除（0：否；1：是）
}
