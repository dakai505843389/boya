package com.hd.clc.boya.service;

import com.hd.clc.boya.common.Result;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public interface IPaymentService {
    public Result getPaySign(Integer userId, Integer selectionMapId, HttpServletRequest request);
    public void wxNotify(HttpServletRequest request, HttpServletResponse response);
}
