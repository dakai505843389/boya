package com.hd.clc.boya.controller;

import com.hd.clc.boya.common.Result;
import com.hd.clc.boya.service.IUtilService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/util")
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@Api(value = "UtilController", tags = "工具Controller")
public class UtilController {

    @Autowired
    private IUtilService utilService;

    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "上传图片接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "image", value = "图片", required = true, dataType = "file", paramType = "form")
    })
    public Result uploadImage(MultipartFile image){
        return utilService.uploadImage(image);
    }

}
