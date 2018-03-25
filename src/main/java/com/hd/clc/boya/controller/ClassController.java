package com.hd.clc.boya.controller;

import com.hd.clc.boya.common.Result;
import com.hd.clc.boya.service.IClassService;
import io.swagger.annotations.*;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RequestMapping("/class")
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@Api(value = "ClassController", tags = "课程Controller")
public class ClassController {

    @Qualifier("classServiceImpl")
    @Autowired
    private IClassService classService;

    @RequestMapping(value = "/releaseClass", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "发布课程接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherId", value = "教师id", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "className", value = "课程名", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "description", value = "课程介绍", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "classRoom", value = "教室", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "maxNumber", value = "最多上课人数", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "classImage", value = "课程图片路径", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "numberLimit", value = "最少开课人数", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "singlePrice", value = "课程单价", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "isAllowGroup", value = "是否允许团购（0：不允许；1：允许）", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "groupPrice", value = "团购价格", required = false, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "groupNumberLimit", value = "拼团人数", required = false, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "classTypeId", value = "课程类型id", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "classBeginTime", value = "课程开始时间（yyyy-MM-dd HH:mm:ss）", required = true, dataType = "Date", paramType = "form"),
            @ApiImplicitParam(name = "classEndTime", value = "课程结束时间（yyyy-MM-dd HH:mm:ss）", required = true, dataType = "Date", paramType = "form"),
    })
    public Result releaseClass(@RequestParam Integer teacherId,
                               @RequestParam String className,
                               @RequestParam String description,
                               @RequestParam String classRoom,
                               @RequestParam Integer maxNumber,
                               @RequestParam String classImage,
                               @RequestParam Integer numberLimit,
                               @RequestParam Integer singlePrice,
                               @RequestParam Integer isAllowGroup,
                               @RequestParam(required = false) Integer groupPrice,
                               @RequestParam(required = false) Integer groupNumberLimit,
                               @RequestParam Integer classTypeId,
                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date classBeginTime,
                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date classEndTime
                               ) throws Exception {
        return classService.releaseClass(teacherId, className, description, classImage, classRoom,
                                         maxNumber, numberLimit,
                                         singlePrice, isAllowGroup, groupPrice, groupNumberLimit,
                                         classTypeId, classBeginTime, classEndTime);
    }

    @RequestMapping(value = "/chooseClass", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "选课接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "classId", value = "课程id", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "isGroup", value = "是否团购（0：否；1：是）", required = true, dataType = "int", paramType = "form"),
    })
    public Result chooseClass(@RequestParam Integer userId,
                              @RequestParam Integer classId,
                              @RequestParam Integer isGroup,
                              HttpServletRequest request
                              ){
        return classService.chooseClass(userId, classId, isGroup, request);
    }

    @RequestMapping(value = "/revokeClass", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "form"),
        @ApiImplicitParam(name = "selectionMapId", value = "选课映射ID", required = true, dataType = "int", paramType = "form")
    })
    @ApiOperation(value = "退课接口")
    public Result revokeClass(@RequestParam int userId, @RequestParam int selectionMapId){
        return classService.revokeClass(userId, selectionMapId);
    }

    @RequestMapping(value = "/beginClass", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "确认上课接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "techerId", value = "教师id", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "classId", value = "课程id", required = true, dataType = "int", paramType = "form"),
    })
    public Result beginClass(@RequestParam Integer teacherId,
                             @RequestParam Integer classId){
        return classService.beginClass(teacherId, classId);
    }

    @RequestMapping(value = "/queryById", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询课程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "classId", value = "课程id", required = true, dataType = "int", paramType = "form")
    })
    public Result queryById(@RequestParam Integer classId){
        return classService.queryById(classId);
    }

}
