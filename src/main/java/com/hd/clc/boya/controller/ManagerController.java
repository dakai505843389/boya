package com.hd.clc.boya.controller;

import com.hd.clc.boya.common.Result;
import com.hd.clc.boya.service.IManagerService;
import com.hd.clc.boya.service.ITeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/back/manager")
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@Api(value = "ManagerController", tags = "管理员Controller")
public class ManagerController {
    @Autowired
    @Qualifier("managerServiceImpl")
    private IManagerService managerService;

    @Autowired
    @Qualifier("teacherServiceImpl")
    private ITeacherService teacherService;


    /**
     * 添加管理员
     * @param account
     * @param password
     */
    @RequestMapping(value = "/addNewManager",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加管理员接口（仅超管可以调用）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "帐号", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "managerType", value = "管理员类型（0：超级管理员；1：普通管理员）", required = true, dataType = "int", paramType = "form"),
    })
    public Result add(@RequestParam String account,
                      @RequestParam String password,
                      @RequestParam Integer managerType,
                      HttpServletRequest request)throws Exception{
        return  managerService.add(account,password,managerType,request);

    }

    /**
     * 登录接口
     * @param
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "管理员登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "帐号", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "form")
    })
    public Result login(@RequestParam String account,
                        @RequestParam String password,
                        HttpServletRequest request) {
        return  managerService.login(account, password, request);
    }

    /*
     * 新增课程类型
     * @param typeName
     * @param request
     * @return
     */
    @RequestMapping(value = "/addNewClassType", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "新增课程类型接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "typeName", value = "类型名", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "imagePath", value = "图片路径", required = true, dataType = "String", paramType = "form")
    })
    public Result addNewClassType(@RequestParam String typeName,
                                  @RequestParam String imagePath,
                                  HttpServletRequest request){
        return managerService.addNewClassType(typeName, imagePath, request);
    }

    /**
     * 教师审核通过接口
     * @param teacherId
     */
    @RequestMapping(value = "/allowTeacher",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "教师审核通过接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherId", value = "教师id", required = true, dataType = "int", paramType = "form"),
    })
    public Result allowTeacher(@RequestParam Integer teacherId){
        return  teacherService.allowTeacher(teacherId);
    }

    /**
     * 暂停教师资格接口
     * @param teacherId
     */
    @RequestMapping(value = "/suspendTeacher",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "暂停教师资格接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "teacherId", value = "教师id", required = true, dataType = "int", paramType = "form"),
    })
    public Result suspendTeacher(@RequestParam Integer teacherId){
        return  teacherService.suspendTeacher(teacherId);
    }

    /**
     * 交换课程排名
     * @param classTypeId1
     * @param classTypeId2
     * @return
     */
    @RequestMapping(value = "/changeClassTypeSortNum",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "交换首页热门课程排名接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "classTypeId1", value = "类型ID1", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "classTypeId2", value = "类型ID2", required = true, dataType = "int", paramType = "form")
    })
    public Result changeClassTypeSortNum(@RequestParam Integer classTypeId1,
                                         @RequestParam Integer classTypeId2){
        return  managerService.changeClassTypeSortNum(classTypeId1,classTypeId2);
    }

    /*
     * 查询待审核课程接口
     * @param request
     * @return
     */
    @RequestMapping(value = "/getAllowingClass", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询待审核课程接口")
    public Result getAllowingClass(HttpServletRequest request){
        return managerService.getAllowingClass(request);
    }

    /**
     * 课程审核通过接口
     * @param classId
     * @return
     */
    @RequestMapping(value = "/allowClass", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "课程审核通过接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "classId", value = "classId", required = true, dataType = "int", paramType = "form"),
    })
    public Result allowClass(@RequestParam Integer classId){
        return managerService.allowClass(classId);
    }
}
