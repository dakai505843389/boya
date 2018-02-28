package com.hd.clc.boya.db.impl;

import com.hd.clc.boya.db.entity.HotClass;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotClassMapper {
    HotClass queryById(@Param("id") Integer id);
    int deleteById(@Param("id") Integer id);
    List<HotClass> query();
    int getMaxSortNum();
    int addNewHotClass(HotClass hotClass);
    int classBegin(@Param("classId") int id);
}
