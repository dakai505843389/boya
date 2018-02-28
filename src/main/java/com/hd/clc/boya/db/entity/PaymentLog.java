package com.hd.clc.boya.db.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class PaymentLog {
    private Integer id;
    private Integer paymentId;
    private String description;
    private Date addTime;
    private Integer isDeleted;//是否删除（0：否；1：是）
}
