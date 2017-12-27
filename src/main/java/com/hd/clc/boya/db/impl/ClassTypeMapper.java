package com.hd.clc.boya.db.impl;

import com.hd.clc.boya.db.entity.ClassType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassTypeMapper {
    ClassType queryById(@Param("id") Integer id);
}
