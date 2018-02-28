package com.hd.clc.boya.controller;

import com.hd.clc.boya.common.Result;
import com.hd.clc.boya.common.ResultDetial;
import com.hd.clc.boya.common.XmlUtil;
import com.hd.clc.boya.service.IPaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

@RequestMapping("/payment")
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
@Api(value = "PaymentController", tags = "支付订单Controller")
public class PaymentController {

    @Qualifier("paymentServiceImpl")
    @Autowired
    private IPaymentService paymentService;

    @RequestMapping(value = "/getPaySign", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取支付签名接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "selectionMapId", value = "选课映射id", required = true, dataType = "int", paramType = "form")
    })
    public Result getPaySign(@RequestParam Integer userId,
                             @RequestParam Integer selectionMapId,
                             HttpServletRequest request){
        return paymentService.getPaySign(userId, selectionMapId, request);
    }


    /**
     * 支付结果通知接口
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/wxNotify", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "支付结果通知接口（微信服务器调用）")
    public void wxNotify(HttpServletRequest request, HttpServletResponse response){
        paymentService.wxNotify(request, response);
    }
}
