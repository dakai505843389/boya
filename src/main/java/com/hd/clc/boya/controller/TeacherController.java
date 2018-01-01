package com.hd.clc.boya.controller;


import com.hd.clc.boya.common.Result;
import com.hd.clc.boya.service.ITeacherService;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/teacher")
@CrossOrigin(value = "*",maxAge = 3600)
@RestController
public class TeacherController {

    @Qualifier("teacherServiceImpl")
    @Autowired
    private ITeacherService teacherService;

    /** 移动至managerController，所有非查询数据操作方法添加Transactional声明式事务管理
     * 教师审核通过接口
     * @param teacherId
     */
    @RequestMapping(value = "/allowTeacher",method = RequestMethod.POST)
    public Result allowTeacher(@RequestParam Integer teacherId){
        return  teacherService.allowTeacher(teacherId);
    }

    /**
     * 暂停教师资格接口
     * @param teacherId
     */
    @RequestMapping(value = "/suspendTeacher",method = RequestMethod.POST)
    public Result suspendTeacher(@RequestParam Integer teacherId){
        return  teacherService.suspendTeacher(teacherId);
    }
}
