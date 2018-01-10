package com.hd.clc.boya.service.impl;

import com.hd.clc.boya.common.BaseVar;
import com.hd.clc.boya.common.FileUtil;
import com.hd.clc.boya.common.Result;
import com.hd.clc.boya.service.IClassService;
import com.hd.clc.boya.db.entity.Class;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Service
public class ClassServiceImpl implements IClassService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result releaseClass(Integer teacherId, String className, String description,
                               MultipartFile classImage, String classRoom, Integer maxNumber,
                               Integer numberLimit, Integer singlePrice,
                               Integer isAllowGroup, Integer groupPrice, Integer groupNumberLimit,
                               Integer classTypeId, Date classBeginTime,
                               Date classEndTime) throws Exception {
        Class classObject = new Class();
        classObject.setBelongTeacherId(teacherId);
        classObject.setClassName(className);
        classObject.setDescription(description);
        String imagePath = BaseVar.USER_IMAGE_PATH + teacherId + "/" + className + "/";
        String fileName = FileUtil.upload4Stream(classImage.getInputStream(), imagePath, classImage.getOriginalFilename());
        String classImagePath = imagePath + fileName;
        classObject.setClassImagePath(classImagePath);
        classObject.setClassRoom(classRoom);
        classObject.setMaxNumber(maxNumber);
        classObject.setNumberLimit(numberLimit);
        classObject.setSinglePrice(singlePrice);
        classObject.setIsAllowGroup(isAllowGroup);
        if (1 == isAllowGroup) {
            if (groupPrice != null) {
                classObject.setGroupPrice(groupPrice);
            } else {
                classObject.setGroupPrice(singlePrice);
            }
            if (groupNumberLimit != null) {
                classObject.setGroupNumberLimit(groupNumberLimit);
            } else {
                classObject.setGroupNumberLimit(1);
            }
        }
        classObject.setClassTypeId(classTypeId);
        classObject.setClassBeginTime(classBeginTime);
        classObject.setClassEndTime(classEndTime);
        classObject.setClassAddTime(new Date(System.currentTimeMillis()));


        return null;
    }
}
