package com.hd.clc.boya.controller;

import com.hd.clc.boya.common.BaseVar;
import com.hd.clc.boya.common.Result;
import com.hd.clc.boya.common.ResultDetial;
import com.hd.clc.boya.common.WxUtil;
import com.hd.clc.boya.task.impl.ClassBeginTask;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RequestMapping("/test")
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
public class TestController {

    @RequestMapping(value = "timerTest", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "classId", value = "classId", required = true, dataType = "int", paramType = "form")
    })
    public Result timerTest(@RequestParam int classId){
        Map<String, Object> data = new HashMap<>();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(classId);
            }
        }, new Date(System.currentTimeMillis() + 1000));
        return new ResultDetial<>(data);
    }

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
    @ResponseBody
    public Result dateTest(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date time) {
        Map<String, Object> data = new HashMap<>();
        data.put("time", time);
        data.put("newTime", new Date(System.currentTimeMillis()));
        return new ResultDetial<>("", data);
    }

    @RequestMapping(value = "getWXACode", method = RequestMethod.POST)
    @ResponseBody
    public Result getWXACode(@RequestParam String sceneStr, @RequestParam int t) throws Exception{
        Map<String, Object> data = new HashMap<>();
        //data.put("wxacode", WxUtil.getminiqrQr(sceneStr, BaseVar.USER_GROUP_WXACODE_PATH + new Date(System.currentTimeMillis())));
        data.put("wxacode", WxUtil.getminiqrQr(sceneStr, "pages/index/index", "C:/Users/Administrator/Desktop/1.png"));
        return new ResultDetial<>("", data);
    }
}
