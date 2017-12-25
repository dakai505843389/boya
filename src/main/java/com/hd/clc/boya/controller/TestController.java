package com.hd.clc.boya.controller;

import com.hd.clc.boya.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RequestMapping("/test")
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
public class TestController {

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public Result test(@RequestParam String name, HttpServletRequest request){
        //String newName = name + new Date(System.currentTimeMillis());
        String res = "ceshi测试";
        return new Result(1, res);
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public Result hello(){
        return new Result(1, "hello");
    }

    public Result test2(){
        return null;
    }
}
