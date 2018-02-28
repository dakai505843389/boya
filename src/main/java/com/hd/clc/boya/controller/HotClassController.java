package com.hd.clc.boya.controller;

import com.hd.clc.boya.common.Result;
import com.hd.clc.boya.service.IHotClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/hotClass")
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@Api(value = "HotClassController", tags = "热门课程Controller")
public class HotClassController {
    @Qualifier("hotClassServiceImpl")
    @Autowired
    private IHotClassService hotClassService;

    /*@RequestMapping(value = "getHotClass", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("查询热门课程列表")
    public Result getHotClass(){
        return null;
    }*/
}
