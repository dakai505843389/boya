package com.hd.clc.boya.service.impl;

import com.hd.clc.boya.common.Result;
import com.hd.clc.boya.common.ResultDetial;
import com.hd.clc.boya.db.entity.Teacher;
import com.hd.clc.boya.db.entity.UserCollectionTeacherMap;
import com.hd.clc.boya.db.impl.TeacherMapper;
import com.hd.clc.boya.db.impl.UserCollectionTeacherMapMapper;
import com.hd.clc.boya.service.IUserCollectionTeacherMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserCollectionTeacherMapServiceImpl implements IUserCollectionTeacherMapService {

    @Autowired
    private UserCollectionTeacherMapMapper userCollectionTeacherMapMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public Result collecteTeacher(int userId, int teacherId) {
        Map<String, Object> data = new HashMap<>();
        String msg;
        if (userCollectionTeacherMapMapper.checkRepeat(userId, teacherId) == 0){
            UserCollectionTeacherMap userCollectionTeacherMap = new UserCollectionTeacherMap();
            userCollectionTeacherMap.setUserId(userId);
            userCollectionTeacherMap.setTeacherId(teacherId);
            userCollectionTeacherMap.setAddTime(new Date(System.currentTimeMillis()));
            userCollectionTeacherMapMapper.add(userCollectionTeacherMap);
            teacherMapper.addNewFans(teacherId);
            msg = "收藏成功！";
        }else {
            msg = "该用户已收藏该教师！";
        }
        return new ResultDetial<>(msg, data);
    }
}
