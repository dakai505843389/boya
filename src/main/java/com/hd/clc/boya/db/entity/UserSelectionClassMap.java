package com.hd.clc.boya.db.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class UserSelectionClassMap {
    private Integer id;//主键值
    private Integer userId;//用户ID
    private Integer classId;//课程ID
    private Integer isGroup;//是否团购（0：否；1：是）
    private Integer isPaid;//是否支付（0：未支付；1：已支付）
    private Integer paymentId;//付款ID
    private Integer status;//课程状态（0：未上课；1：已上课；2：退课）
    private Integer isEvalueted;//是否已评价（0：否；1：是）
    private Date    addTime;//增加时间
    private Date    updateTime;//更新时间
    private Integer isDeleted;//是否删除（0：未删除；1：已删除）

}
