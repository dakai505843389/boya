package com.hd.clc.boya.db.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class Payment {
    private Integer id;//数据库主键
    private Date    addTime;//增加时间
    private Integer isDeleted;//是否删除（0：否；1：是）
}
