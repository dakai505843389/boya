package com.hd.clc.boya.service.impl;

import com.hd.clc.boya.db.entity.ClassLog;
import com.hd.clc.boya.db.impl.ClassLogMapper;
import com.hd.clc.boya.service.IClassLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassLogServiceImpl implements IClassLogService {

    @Autowired
    private ClassLogMapper classLogMapper;

    @Override
    public void log(int classId, String description) {
        ClassLog classLog = new ClassLog();
        classLog.setClassId(classId);
        classLog.setDescription(description);
        classLogMapper.log(classLog);
    }
}
