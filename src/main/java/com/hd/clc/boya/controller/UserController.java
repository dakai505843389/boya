package com.hd.clc.boya.controller;

import com.hd.clc.boya.common.Result;
import com.hd.clc.boya.common.ResultDetial;
import com.hd.clc.boya.common.WxUtil;
import com.hd.clc.boya.service.IUserService;
import com.hd.clc.boya.vo.UserCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/user")
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 登录接口
     * @param code
     * @param request
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestParam String code, HttpServletRequest request){
        Map<String, Object> data = new HashMap<>();
        // 通过code与微信服务器通信，获取openid
        UserCode userCode = WxUtil.getUserCode(code);



        data.put("userCode", userCode);
        return new ResultDetial<>(data);
    }

    @RequestMapping(value = "/getPersonlInformation")
    public Result getPersonlInformation(@RequestParam Integer userId){
        return null;
    }



    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public Result test(@RequestParam Integer id){
        return userService.query(id);
    }

}
