package com.hd.clc.boya.service;

import com.hd.clc.boya.common.ResultDetial;
import com.hd.clc.boya.db.entity.Teacher;
import com.hd.clc.boya.db.impl.TeacherMapper;
import com.hd.clc.boya.service.impl.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TeacherService implements TeacherServiceImpl{

    @Autowired
    private TeacherMapper teacherMapper;

   @Override
    public ResultDetial query(Integer id) {
        Map<String, Object> data = new HashMap<>();
        Teacher teacher =teacherMapper.queryById(id);
        data.put("teacher",teacher);
        return new ResultDetial(data);
    }
}
