package com.hd.clc.boya.controller;

import com.hd.clc.boya.common.Result;
import com.hd.clc.boya.service.IClassRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/classRoom")
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
public class ClassRoomController {
    @Qualifier("classRoomServiceImpl")
    @Autowired
    private IClassRoomService classRoomService;

    /**
     * 教室列表
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result list(){
        return  classRoomService.list();
    }

}
