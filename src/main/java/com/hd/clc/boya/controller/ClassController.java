package com.hd.clc.boya.controller;

import com.hd.clc.boya.common.Result;
import com.hd.clc.boya.service.IClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@RequestMapping("/class")
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
public class ClassController {

    @Qualifier("classServiceImpl")
    @Autowired
    private IClassService classService;

    @RequestMapping(value = "releaseClass", method = RequestMethod.POST)
    public Result releaseClass(@RequestParam Integer teacherId,
                               @RequestParam String className,
                               @RequestParam String description,
                               @RequestParam String classRoom,
                               @RequestParam Integer maxNumber,
                               @RequestParam MultipartFile classImage,
                               @RequestParam Integer numberLimit,
                               @RequestParam Integer singlePrice,
                               @RequestParam Integer isAllowGroup,
                               @RequestParam(required = false) Integer groupPrice,
                               @RequestParam(required = false) Integer groupNumberLimit,
                               @RequestParam Integer classTypeId,
                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss") Date classBeginTime,
                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss") Date classEndTime
                               ) throws Exception {
        return classService.releaseClass(teacherId, className, description, classImage, classRoom,
                                         maxNumber, numberLimit,
                                         singlePrice, isAllowGroup, groupPrice, groupNumberLimit,
                                         classTypeId, classBeginTime, classEndTime);
    }

}
