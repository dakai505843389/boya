package com.hd.clc.boya.db.impl;

import com.hd.clc.boya.db.entity.ClassType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassTypeMapper {
    ClassType queryById(@Param("id") Integer id);
    int addNewClassType(ClassType classType);
    Integer getMaxSortNum();
    List<ClassType> list();
    int changeClassTypeSortNum(ClassType classType);
}
