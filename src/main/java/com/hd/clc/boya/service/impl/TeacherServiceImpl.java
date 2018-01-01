package com.hd.clc.boya.service.impl;

import com.hd.clc.boya.common.ResultDetial;
import com.hd.clc.boya.db.entity.Teacher;
import com.hd.clc.boya.db.entity.TeacherEvaluation;
import com.hd.clc.boya.db.entity.User;
import com.hd.clc.boya.db.impl.TeacherMapper;
import com.hd.clc.boya.db.impl.UserMapper;
import com.hd.clc.boya.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TeacherServiceImpl implements ITeacherService {

    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private UserMapper userMapper;

   @Override
    public ResultDetial query(Integer id) {
        Map<String, Object> data = new HashMap<>();
        Teacher teacher =teacherMapper.queryById(id);
        data.put("teacher",teacher);
        return new ResultDetial(data);
    }
    @Override
    public ResultDetial allowTeacher(Integer teacherId){
       Map<String,Object> data = new HashMap<>();
       String msg = null;
       Teacher teacher = teacherMapper.queryById(teacherId);
       if(teacher == null){
           return new ResultDetial<>(-1, "没有该教师！", data);
       }else{
           User user = userMapper.queryById(teacher.getUserId());
           if(teacher.getStatus()==0 && user.getUserType()==0){
               user.setUserType(1);
               teacher.setStatus(1);
               if(teacherMapper.changeStatus(teacher)<1 || userMapper.updateUserType(user)<1){
                   return new ResultDetial<>(-1, "修改失败！", data);
               }else{
                   msg="教师审核成功";
                   data.put("teacher",teacher);
               }

           }

       }
        return new ResultDetial<>(msg, data);
    }


    @Override
    public ResultDetial suspendTeacher(Integer teacherId){
        Map<String,Object> data = new HashMap<>();
        String msg = null;
        Teacher teacher = teacherMapper.queryById(teacherId);
        if(teacher == null){
            return new ResultDetial<>(-1, "没有该教师！", data);
        }else{
            User user = userMapper.queryById(teacher.getUserId());
            if(teacher.getStatus()==1 && user.getUserType()==1){
                user.setUserType(0);
                teacher.setStatus(2);
                if(teacherMapper.changeStatus(teacher)<1 || userMapper.updateUserType(user)<1){
                    return new ResultDetial<>(-1, "修改失败！", data);
                }else{
                    msg="暂停教师成功";
                    data.put("teacher",teacher);
                }

            }

        }
        return new ResultDetial<>(msg, data);
    }
}
