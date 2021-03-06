package com.hd.clc.boya.service.impl;

import com.hd.clc.boya.common.*;
import com.hd.clc.boya.service.IUtilService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class UtilService implements IUtilService {

    @Override
    public Result uploadImage(MultipartFile image, HttpServletRequest request) {
        Map<String, Object> data = new HashMap<>();
        // 进行文件上传操作
        String fileUrl = null;
        String msg = null;
        String path = null;
        try {
            path = request.getSession().getServletContext().getRealPath("/");
            fileUrl = FileUtil.upload4Stream(image.getInputStream(), path + BaseVar.UPLOAD_IMAGE_PATH, image.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!StringUtil.checkEmpty(fileUrl)) {
            msg = "图片上传失败！";
        }else {
            msg = "图片上传成功！";
            data.put("imagePath", BaseVar.UPLOAD_IMAGE_PATH + fileUrl);
        }
        return new ResultDetial<>(msg, data);
    }
}
