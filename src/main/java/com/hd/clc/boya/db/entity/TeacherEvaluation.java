package com.hd.clc.boya.db.entity;

import java.util.Date;

public class TeacherEvaluation {
    private Integer id;//数据库主键
    private Integer mapId;//选课映射表ID
    private Integer belongClassId;//所属课程ID
    private Integer belongTeacherId;//所属教师ID
    private Integer evaluationType;//评价类型（0：文字评价；1：打星一；2：打星二；3：打星三）
    private String  evalueationWord;//评语
    private Integer evalueationScore;//评分
    private Date    addTime;//增加时间
    private Date    updateTime;//更新时间
    private Integer isDeleted;//是否删除（0：否；1：是）
}
