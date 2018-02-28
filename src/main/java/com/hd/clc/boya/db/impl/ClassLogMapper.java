package com.hd.clc.boya.db.impl;

import com.hd.clc.boya.db.entity.ClassLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassLogMapper {
    ClassLog queryById(@Param("id") int id);
    int log(ClassLog classLog);
}
