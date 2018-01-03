package com.hd.clc.boya.service.impl;

import com.hd.clc.boya.common.ResultDetial;
import com.hd.clc.boya.db.entity.ClassType;
import com.hd.clc.boya.db.impl.ClassMapper;
import com.hd.clc.boya.db.impl.ClassTypeMapper;
import com.hd.clc.boya.service.IClassTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClassTypeServiceImpl implements IClassTypeService {

    @Autowired
    private ClassTypeMapper classTypeMapper;

    @Override
    public ResultDetial list() {
        Map<String, Object> data = new HashMap<>();
        String msg = null;
        List<ClassType> classTypeList = classTypeMapper.list();
        if (classTypeList == null){
            return new ResultDetial<>(-1, "返回失败！", data);
        }else {
            data.put("classTypeList",classTypeList);
            msg = "获取成功";
        }



        return new ResultDetial<>(msg, data);
    }
}
