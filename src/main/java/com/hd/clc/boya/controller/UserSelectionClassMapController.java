package com.hd.clc.boya.controller;

import com.hd.clc.boya.service.IUserSelectionClassMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/userSelectionClassMap")
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
public class UserSelectionClassMapController {
    @Autowired
    private IUserSelectionClassMapService userSelectionClassMapService;
}
