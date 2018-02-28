package com.hd.clc.boya.db.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class Payment {
    private Integer id;//数据库主键
    private Integer userId;
    private Integer teacherId;
    private Integer classId;
    private Integer selectionMapId;
    private Integer price;
    private Integer status;//0：支付开启；1：支付完成；2：支付失败
    private Date payTime;
    private Date addTime;//增加时间
    private Date updateTime;
    private Integer isDeleted;//是否删除（0：否；1：是）
}
