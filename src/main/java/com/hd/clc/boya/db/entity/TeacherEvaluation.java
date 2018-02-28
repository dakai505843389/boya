package com.hd.clc.boya.db.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class TeacherEvaluation {
    private Integer id;//数据库主键
    private Integer mapId;//选课映射表ID
    private Integer belongClassId;//所属课程ID
    private Integer belongTeacherId;//所属教师ID
    private String  evalueationWord;//评语
    private Integer evalueationScoreForFirst;//评分1
    private Integer evalueationScoreForSecond;//评分2
    private Integer evalueationScoreForThird;//评分3
    private Date    addTime;//增加时间
    private Date    updateTime;//更新时间
    private Integer isDeleted;//是否删除（0：否；1：是）
}
