package com.hd.clc.boya.service;

import com.hd.clc.boya.common.ResultDetial;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public interface IUserService {
    ResultDetial login(String code);
    ResultDetial modifyUser(Integer userId, String userName, String image, Integer sex, String tel) throws Exception;
    ResultDetial applyTeacher(Integer userId, String imagePath, String name, String tel, String description, String specialize, String experience, Integer teacherType);
}
