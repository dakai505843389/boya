package com.hd.clc.boya.controller;


import com.hd.clc.boya.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/teacher")
@CrossOrigin(value = "*",maxAge = 3600)
@RestController
public class TeacherController {

    @Qualifier("teacherServiceImpl")
    @Autowired
    private ITeacherService teacherService;


}
