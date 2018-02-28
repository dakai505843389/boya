package com.hd.clc.boya.controller;

import com.hd.clc.boya.common.Result;
import com.hd.clc.boya.service.IClassTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/classType")
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@Api(value = "ClassTypeController", tags = "课程类型Controller")
public class ClassTypeController {
    @Qualifier("classTypeServiceImpl")
    @Autowired
    private IClassTypeService classTypeService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询课程类型接口")
    public Result list(){
        return classTypeService.list();
    }
}
