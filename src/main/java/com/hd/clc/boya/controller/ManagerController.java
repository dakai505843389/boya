package com.hd.clc.boya.controller;

import com.hd.clc.boya.common.Result;
import com.hd.clc.boya.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/manager")
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
public class ManagerController {
    @Autowired
    @Qualifier("managerServiceImpl")
    private IManagerService managerService;


    /**
     * 注册接口
     * @param account
     * @param password
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Result register(@RequestParam String account,
                           @RequestParam String password)throws Exception{
        return  managerService.register(account,password);

    }

    /**
     * 登录接口
     * @param
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestParam String account,
                        @RequestParam String password) {
        return  managerService.login(account,password);
    }


}
