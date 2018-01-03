package com.hd.clc.boya.service;

import com.hd.clc.boya.common.Result;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Repository
public interface IClassService {
    Result releaseClass(Integer teacherId, String className, String description,
                        MultipartFile classImage, String classRoom, Integer maxNumber,
                        Integer numberLimit, Integer singlePrice,
                        Integer isAllowGroup, Integer groupPrice, Integer groupNumberLimit,
                        Integer classTypeId, Date classBeginTime,
                        Date classEndTime) throws Exception;
}
