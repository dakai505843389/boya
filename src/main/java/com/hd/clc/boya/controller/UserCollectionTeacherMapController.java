package com.hd.clc.boya.controller;

import com.hd.clc.boya.common.Result;
import com.hd.clc.boya.service.IUserCollectionTeacherMapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/userCollectionTeacherMap")
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@Api(value = "UserCollectionTeacherMapController", tags = "收藏教师Controller")
public class UserCollectionTeacherMapController {

    @Qualifier("userCollectionTeacherMapServiceImpl")
    @Autowired
    private IUserCollectionTeacherMapService userCollectionTeacherMapService;

    @RequestMapping(value = "/collecteTeacher", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "收藏教师")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "form"),
        @ApiImplicitParam(name = "teacherId", value = "教师ID", required = true, dataType = "int", paramType = "form")
    })
    public Result collecteTeacher(int userId, int teacherId){
        return userCollectionTeacherMapService.collecteTeacher(userId, teacherId);
    }

}
