package com.hd.clc.boya.db.impl;

import com.hd.clc.boya.db.entity.TeacherEvaluation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherEvaluationMapper {
    TeacherEvaluation queryById(@Param("id") Integer id);
}
