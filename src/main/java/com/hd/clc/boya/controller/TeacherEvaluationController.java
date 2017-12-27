package com.hd.clc.boya.controller;

import com.hd.clc.boya.service.impl.TeacherEvaluationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/teacherEvaluation")
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
public class TeacherEvaluationController {
    @Autowired
    private TeacherEvaluationServiceImpl teacherEvaluationService;
}
