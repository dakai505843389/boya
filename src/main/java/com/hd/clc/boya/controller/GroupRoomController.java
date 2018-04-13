package com.hd.clc.boya.controller;

import com.hd.clc.boya.common.Result;
import com.hd.clc.boya.service.IGroupRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/groupRoom")
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@Api(value = "GroupRoomController", tags = "团购Controller")
public class GroupRoomController {

    @Qualifier("groupRoomServiceImpl")
    @Autowired
    IGroupRoomService groupRoomService;

    @RequestMapping(value = "/scanCodeForGroup", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "扫码加入团购")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "sceneStr", value = "sceneStr", required = true, dataType = "String", paramType = "form")
    })
    public Result scanCodeForGroup(@RequestParam Integer userId, @RequestParam String sceneStr){
        return groupRoomService.scanCodeForGroup(userId, sceneStr);
    }


    @RequestMapping(value = "/queryForAllGroupNumber", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "classId", value = "课程ID", required = true, dataType = "int", paramType = "form")
    })
    @ApiOperation(value = "查询团购所有成员")
    public Result queryForAllGroupNumber(@RequestParam int userId, @RequestParam int classId){
        return groupRoomService.queryForAllGroupNumber(userId, classId);
    }

    @RequestMapping(value = "/getCode", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "classId", value = "课程ID", required = true, dataType = "int", paramType = "form")
    })
    @ApiOperation(value = "获取团购二维码")
    public Result getCode(@RequestParam int userId, @RequestParam int classId){
        return groupRoomService.getCode(userId, classId);
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupRoomId", value = "团购ID", required = true, dataType = "int", paramType = "form"),
    })
    @ApiOperation(value = "通过团购ID查询所有信息")
    public Result query(@RequestParam int groupRoomId){
        return groupRoomService.query(groupRoomId);
    }


}
