package com.hd.clc.boya.db.impl;

import com.hd.clc.boya.db.entity.Class;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassMapper {
    Class queryById(@Param("id") Integer id);
    int addNewClass(Class classObject);
    List<Class> getAllowingClass();
    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);
    int modifyPaidAmount(Class classObject);
    int updateNumber(Class classObject);
    int classBegin(@Param("id") int id);
    int shutClass(@Param("id") int id);
    int shutGroup(@Param("id") int id);
    List<Class> queryFinishedClassByTeacherId(@Param("belongTeacherId") int belongTeacherId);
    List<Class> queryOngoingClassByTeacherId(@Param("belongTeacherId") int belongTeacherId);
}
