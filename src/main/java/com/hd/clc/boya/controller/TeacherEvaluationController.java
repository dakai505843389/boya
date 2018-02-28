package com.hd.clc.boya.controller;

import com.hd.clc.boya.common.Result;
import com.hd.clc.boya.service.ITeacherEvaluationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/teacherEvaluation")
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@Api(value = "TeacherEvaluationController", tags = "教师评价Controller")
public class TeacherEvaluationController {

    @Qualifier("teacherEvaluationServiceImpl")
    @Autowired
    private ITeacherEvaluationService teacherEvaluationService;

    @RequestMapping(value = "/evaluate", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "课程评价接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "mapId", value = "选课映射ID", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "evalueationWord", value = "文字评价（非必填）", required = false, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "scoreOne", value = "打分一", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "scoreTwo", value = "打分二", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "scoreThree", value = "打分三", required = true, dataType = "int", paramType = "form")
    })
    public Result evaluate(int userId, int mapId, String evalueationWord, int scoreOne, int scoreTwo, int scoreThree){
        return teacherEvaluationService.evaluate(userId, mapId, evalueationWord, scoreOne, scoreTwo, scoreThree);
    }
}
