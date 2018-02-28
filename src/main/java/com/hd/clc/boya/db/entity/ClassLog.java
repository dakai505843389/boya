package com.hd.clc.boya.db.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClassLog {
    private int id;
    private int classId;
    private String description;//日志信息
}
