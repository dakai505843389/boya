package com.hd.clc.boya.db.impl;

import com.hd.clc.boya.db.entity.Teacher;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherMapper {
    Teacher queryById(@Param("id") Integer id);
    int deleteById(@Param("id") Integer id);
    Teacher queryByUserId(@Param("userId") Integer userId);
    int changeStatus(Teacher teacher);
}
