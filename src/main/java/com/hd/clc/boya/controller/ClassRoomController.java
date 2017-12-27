package com.hd.clc.boya.controller;

import com.hd.clc.boya.service.IClassRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/classRoom")
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
public class ClassRoomController {
    @Autowired
    private IClassRoomService classRoomService;
}
