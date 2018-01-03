package com.hd.clc.boya.controller;

import com.hd.clc.boya.common.Result;
import com.hd.clc.boya.common.ResultDetial;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/test")
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
public class TestController {

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public Result test(@RequestParam String name, HttpServletRequest request){
        //String newName = name + new Date(System.currentTimeMillis());
        String res = "ceshi测试 ";
        return new Result(1, res);
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public Result hello(){
        return new Result(1, "hello");
    }

    @RequestMapping(value = "dateTest", method = RequestMethod.POST)
    public Result dateTest(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss") Date time)
    {
        Map<String, Object> data = new HashMap<>();
        data.put("time", time);
        data.put("newTime", new Date(System.currentTimeMillis()));
        return new ResultDetial<>(data);
    }
}
