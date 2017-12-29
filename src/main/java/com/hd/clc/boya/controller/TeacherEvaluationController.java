package com.hd.clc.boya.controller;

import com.hd.clc.boya.service.ITeacherEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/teacherEvaluation")
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
public class TeacherEvaluationController {
    @Qualifier("teacherEvaluationServiceImpl")
    @Autowired
    private ITeacherEvaluationService teacherEvaluationService;
}
