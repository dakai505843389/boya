package com.hd.clc.boya.db.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class Class {
    private Integer id;//数据库主键
    private Integer belongTeacherId;//课程所属教师ID
    private String className;//课程名
    private String description;//课程描述
    private String classImagePath;//课程图片路径
    private Integer numberLimit;//最少开课人数
    private Integer countNumber;//已选总人数
    private Integer singlePrice;//单人次价格
    private Integer isAllowGroup;//是否允许团购（0：不允许；1：允许）
    private Integer groupPrice;//团购价
    private Integer groupNumberLimit;//团购人数限制
    private Integer classTypeId;//课程类型ID
    private Integer classRoomId;//课程教室ID
    private Date classBeginTime;//课程开始时间
    private Date classEndTime;//课程结束时间
    private Date classAddTime;//添加课程时间
    private Date classUpdateTime;//修改课程时间
    private Integer status;//课程状态（0：待审核；1：暂停；2：已发布；3：已结束）
    private Integer isDeleted;//是否删除（0：未删除；1：已删除）
}
