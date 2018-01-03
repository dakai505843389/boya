package com.hd.clc.boya.db.impl;

import com.hd.clc.boya.db.entity.Class;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;

@Repository
public interface ClassMapper {
    Class queryById(@Param("id") Integer id);

}
