package com.hd.clc.boya.db.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class PaySign {
    private int id;
    private String nonceStr;
    private String prepay_id;
    private String timeStamp;
    private String paySign;
    private Date addTime;
    private int isDeleted;
}
