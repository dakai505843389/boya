package com.hd.clc.boya.service;

import com.hd.clc.boya.common.ResultDetial;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public interface IUserService {
    ResultDetial login(String code);
    ResultDetial modifyUser(Integer userId, String userName, MultipartFile image, Integer sex, String tel) throws Exception;
    ResultDetial applyTeacher(Integer userId, String description, Integer teacherType);
}
