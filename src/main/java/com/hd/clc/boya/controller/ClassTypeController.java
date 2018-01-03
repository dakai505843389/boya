package com.hd.clc.boya.controller;

import com.hd.clc.boya.common.Result;
import com.hd.clc.boya.service.IClassTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/classType")
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
public class ClassTypeController {
    @Qualifier("classTypeServiceImpl")
    @Autowired
    private IClassTypeService classTypeService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result list(){
        return classTypeService.list();
    }
}
