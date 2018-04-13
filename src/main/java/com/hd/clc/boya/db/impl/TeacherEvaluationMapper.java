package com.hd.clc.boya.db.impl;

import com.hd.clc.boya.db.entity.TeacherEvaluation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherEvaluationMapper {
    TeacherEvaluation queryById(@Param("id") Integer id);
    int add(TeacherEvaluation teacherEvaluation);
    List<TeacherEvaluation> queryByTeacherId(@Param("belongTeacherId") int belongTeacherId);
}
