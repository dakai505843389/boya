package com.hd.clc.boya.controller;

import com.hd.clc.boya.common.Result;
import com.hd.clc.boya.service.IUserSelectionClassMapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/userSelectionClassMap")
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@Api(value = "UserSelectionClassMapController", tags = "用户选课映射Controller")
public class UserSelectionClassMapController {

    @Qualifier("userSelectionClassMapServiceImpl")
    @Autowired
    private IUserSelectionClassMapService userSelectionClassMapService;

    @RequestMapping(value = "query", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "classId", value = "课程ID", required = true, dataType = "int", paramType = "form")
    })
    @ApiOperation(value = "查询订单（选课映射）")
    public Result query(int userId, int classId){
        return userSelectionClassMapService.query(userId, classId);
    }
}
