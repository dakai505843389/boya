package com.hd.clc.boya.service;

import com.hd.clc.boya.common.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Service
public interface IUtilService {
    Result uploadImage(MultipartFile image, HttpServletRequest request);
}
