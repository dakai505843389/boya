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

    @RequestMapping(value = "scanCodeForGroup/", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "扫码加入团购")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "classId", value = "课程id", required = true, dataType = "int", paramType = "form"),
    })
    public Result scanCodeForGroup(Integer userId, String sceneStr){
        return groupRoomService.scanCodeForGroup(userId, sceneStr);
    };

}
