package com.hd.clc.boya.controller;


import com.hd.clc.boya.common.Result;
import com.hd.clc.boya.service.ITeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/teacher")
@CrossOrigin(value = "*",maxAge = 3600)
@RestController
@Api(value = "TeacherController", tags = "教师Controller")
public class TeacherController {

    @Qualifier("teacherServiceImpl")
    @Autowired
    private ITeacherService teacherService;

    @RequestMapping(value = "/getByUserId", method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户表ID", required = true, dataType = "int", paramType = "form")
    })
    @ApiOperation(value = "获取教师信息")
    public Result getByUserId(@RequestParam Integer userId){
        return teacherService.getByUserId(userId);
    }


    @RequestMapping(value = "queryFinishedClassByTeacherId", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "通过教师ID查询已结束课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherId", value = "教师ID", required = true, dataType = "int", paramType = "form")
    })
    public Result queryFinishedClassByTeacherId(@RequestParam int teacherId){
        return teacherService.queryFinishedClassByTeacherId(teacherId);
    }

    @RequestMapping(value = "queryOngoingClassByTeacherId", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "通过教师ID查询进行中课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherId", value = "教师ID", required = true, dataType = "int", paramType = "form")
    })
    public Result queryOngoingClassByTeacherId(@RequestParam int teacherId){
        return teacherService.queryOngoingClassByTeacherId(teacherId);
    }

}
