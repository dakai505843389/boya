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


}
