package com.hd.clc.boya.service;

import com.hd.clc.boya.common.Result;
import com.hd.clc.boya.common.ResultDetial;
import org.springframework.stereotype.Repository;

@Repository
public interface ITeacherService {
    ResultDetial query(Integer id);
    ResultDetial allowTeacher(Integer teacherId);
    ResultDetial suspendTeacher(Integer teacherId);
    ResultDetial getByUserId(int userId);
}
