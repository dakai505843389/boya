package com.hd.clc.boya.db.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ManagerSession {
    private String SID;
    private Integer managerId;
    private Integer status;
    private Date addTime;
    private Date outTime;
    private Integer isDeleted;
}
