package com.hd.clc.boya.controller;

import com.hd.clc.boya.common.Result;
import com.hd.clc.boya.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/user")
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@Api(value = "UserController", tags = "用户Controller")
public class UserController {

    @Autowired
    @Qualifier("userServiceImpl")
    private IUserService userService;

    /**
     * 登录接口
     * @param code
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "用户登录接口")
    public Result login(@RequestParam String code) {
        return userService.login(code);
    }

    /**
     * 更新用户信息接口
     * @param userId
     * @param userName
     * @param image
     * @param sex
     * @param tel
     * @return
     */
    @RequestMapping(value = "/modifyUser", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "更新用户信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "userName", value = "用户名", required = false, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "image", value = "图片路径", required = false, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "sex", value = "性别（0：女；1：男）", required = false, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "tel", value = "手机号", required = false, dataType = "String", paramType = "form")
    })
    public Result modifyUser(@RequestParam Integer userId,
                             @RequestParam(required = false) String userName,
                             @RequestParam(required = false) String image,
                             @RequestParam(required = false) Integer sex,
                             @RequestParam(required = false) String tel) throws Exception{
        return userService.modifyUser(userId, userName, image, sex, tel);
    }

    /**
     * 申请成为教师
     * @param userId
     * @param description
     * @param teacherType
     * @return
     */
    @RequestMapping(value = "/applyTeacher", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "申请成为教师接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "description", value = "介绍", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "teacherType", value = "教师类型（0：达人；1：机构）", required = true, dataType = "int", paramType = "form")
    })
    public Result applyTeacher(@RequestParam Integer userId,
                               @RequestParam String description,
                               @RequestParam Integer teacherType){
        return userService.applyTeacher(userId, description, teacherType);
    }




}
