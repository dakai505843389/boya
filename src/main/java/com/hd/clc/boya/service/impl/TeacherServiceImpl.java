package com.hd.clc.boya.service.impl;

import com.hd.clc.boya.common.ResultDetial;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherServiceImpl {
    ResultDetial query(Integer id);
}
