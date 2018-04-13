package com.hd.clc.boya.db.impl;

import com.hd.clc.boya.db.entity.Teacher;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherMapper {
    Teacher queryById(@Param("id") Integer id);
    int deleteById(@Param("id") Integer id);
    Teacher queryByUserId(@Param("userId") Integer userId);
    int addNewTeacher(Teacher teacher);
    int changeStatus(Teacher teacher);
    int updateScore(Teacher teacher);
    int addNewFans(@Param("id") int id);
    List<Teacher> getTeacherByStatus(@Param("status") int status);
}
