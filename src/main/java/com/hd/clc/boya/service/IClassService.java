package com.hd.clc.boya.service;

import com.hd.clc.boya.common.Result;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Repository
public interface IClassService {
    Result releaseClass(Integer teacherId, String className, String description,
                        String classImage, String classRoom, Integer maxNumber,
                        Integer numberLimit, Integer singlePrice,
                        Integer isAllowGroup, Integer groupPrice, Integer groupNumberLimit,
                        Integer classTypeId, Date classBeginTime,
                        Date classEndTime) throws Exception;
    Result chooseClass(Integer userId, Integer classId, Integer isGroup, HttpServletRequest request);
    Result beginClass(Integer teacherId, Integer classId);
    Result queryById(int classId);
    Result revokeClass(int userId, int selectionMapId);
}
