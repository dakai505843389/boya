package com.hd.clc.boya.controller;

import com.hd.clc.boya.common.Result;
import com.hd.clc.boya.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/user")
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
public class UserController {

    @Autowired
    @Qualifier("userServiceImpl")
    private IUserService userService;

    /**
     * 登录接口
     * @param code
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestParam String code) {
        return userService.login(code);
    }

    /**
     * 更新用户信息接口
     * @param userId
     * @param userName
     * @param image
     * @param sex
     * @param tel
     * @return
     */
    @RequestMapping(value = "/modifyUser", method = RequestMethod.POST)
    public Result modifyUser(@RequestParam Integer userId,
                             @RequestParam(required = false) String userName,
                             @RequestParam(required = false) MultipartFile image,
                             @RequestParam(required = false) Integer sex,
                             @RequestParam(required = false) String tel) throws Exception{
        return userService.modifyUser(userId, userName, image, sex, tel);
    }

    /**
     * 申请成为教师
     * @param userId
     * @param description
     * @param teacherType
     * @return
     */
    @RequestMapping(value = "/applyTeacher", method = RequestMethod.POST)
    public Result applyTeacher(@RequestParam Integer userId,
                               @RequestParam String description,
                               @RequestParam Integer teacherType){
        return userService.applyTeacher(userId, description, teacherType);
    }


}
