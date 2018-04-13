package com.hd.clc.boya.db.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class GroupRoom {
    private Integer id;
    private Integer organizerId;
    private Integer classId;//课程id
    private String wxacode;//小程序码地址
    private Integer countNum;//当前车内人数
    private Integer maxNum;//车人数上限
    private Integer paidNum;//已支付人数
    private Integer status;//0：等待；1：人满；2：已完成；3：停止
    private Date addTime;//添加时间
    private Date updateTime;//更新时间
    private Integer isDeleted;//是否删除（0：否；1：是）
}
