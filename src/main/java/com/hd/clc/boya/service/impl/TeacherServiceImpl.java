package com.hd.clc.boya.service.impl;

import com.hd.clc.boya.common.ResultDetial;
import com.hd.clc.boya.db.entity.Class;
import com.hd.clc.boya.db.entity.Teacher;
import com.hd.clc.boya.db.entity.TeacherEvaluation;
import com.hd.clc.boya.db.entity.User;
import com.hd.clc.boya.db.impl.ClassMapper;
import com.hd.clc.boya.db.impl.TeacherMapper;
import com.hd.clc.boya.db.impl.UserMapper;
import com.hd.clc.boya.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeacherServiceImpl implements ITeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ClassMapper classMapper;

    @Override
    public ResultDetial query(Integer id) {
        Map<String, Object> data = new HashMap<>();
        Teacher teacher = teacherMapper.queryById(id);
        if (teacher != null) {
            data.put("teacher", teacher);
            return new ResultDetial<>("查询成功！", data);
        } else {
            return new ResultDetial<>(-1, "查询失败", data);
        }
    }

    @Override
    public ResultDetial getAllowingTeacher() {
        Map<String, Object> data = new HashMap<>();
        String msg = null;
        try {
            List<Teacher> teacherList = teacherMapper.getTeacherByStatus(0);
            data.put("teacherList", teacherList);
            msg = "查询成功！";
        }catch (Exception e){
            msg = "查询失败！";
        }
        return new ResultDetial<>(msg, data);
    }

    @Override
    @Transactional
    public ResultDetial allowTeacher(Integer teacherId) {
        Map<String, Object> data = new HashMap<>();
        String msg = null;
        Teacher teacher = teacherMapper.queryById(teacherId);
        if (teacher == null) {
            return new ResultDetial<>(-1, "没有该教师！", data);
        } else {
            User user = userMapper.queryById(teacher.getUserId());
            if (teacher.getStatus() == 0 && user.getUserType() == 2) {
                user.setUserType(1);
                teacher.setStatus(1);
                teacherMapper.changeStatus(teacher);
                userMapper.updateUserType(user);
                msg = "教师审核成功";
                data.put("teacher", teacher);
            }

        }
        return new ResultDetial<>(msg, data);
    }


    @Override
    @Transactional
    public ResultDetial suspendTeacher(Integer teacherId) {
        Map<String, Object> data = new HashMap<>();
        String msg = null;
        Teacher teacher = teacherMapper.queryById(teacherId);
        if (teacher == null) {
            return new ResultDetial<>(-1, "没有该教师！", data);
        } else {
            User user = userMapper.queryById(teacher.getUserId());
            if (teacher.getStatus() == 1 && user.getUserType() == 1) {
                user.setUserType(0);
                teacher.setStatus(2);
                if (teacherMapper.changeStatus(teacher) < 1) {
                    if (userMapper.updateUserType(user) < 1) {
                        return new ResultDetial<>(-1, "修改失败！", data);
                    }

                } else {
                    msg = "暂停教师成功";
                    data.put("teacher", teacher);
                }

            }

        }
        return new ResultDetial<>(msg, data);
    }

    @Override
    public ResultDetial getByUserId(int userId) {
        Map<String, Object> data = new HashMap<>();
        Teacher teacher = teacherMapper.queryByUserId(userId);
        data.put("teacher", teacher);
        return new ResultDetial<>("查询成功", data);
    }

    @Override
    public ResultDetial queryFinishedClassByTeacherId(int teacherId) {
        Map<String, Object> data = new HashMap<>();
        List<Class> classList = classMapper.queryFinishedClassByTeacherId(teacherId);
        data.put("classList", classList);
        return new ResultDetial<>("查询成功", data);
    }

    @Override
    public ResultDetial queryOngoingClassByTeacherId(int teacherID) {
        Map<String, Object> data = new HashMap<>();
        List<Class> classList = classMapper.queryOngoingClassByTeacherId(teacherID);
        data.put("classList", classList);
        return new ResultDetial<>("查询成功", data);
    }
}
