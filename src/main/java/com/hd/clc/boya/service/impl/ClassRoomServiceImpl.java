package com.hd.clc.boya.service.impl;

import com.hd.clc.boya.common.ResultDetial;
import com.hd.clc.boya.db.entity.ClassRoom;
import com.hd.clc.boya.db.impl.ClassRoomMapper;
import com.hd.clc.boya.service.IClassRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClassRoomServiceImpl implements IClassRoomService{
    @Autowired
    private ClassRoomMapper classRoomMapper;

    @Override
    public ResultDetial list(){
        Map<String, Object> data = new HashMap<>();
        String msg = null;
        List<ClassRoom> classRoomList = classRoomMapper.list();
        if (classRoomList == null){
            return new ResultDetial<>(-1, "返回失败！", data);
        }else {
            data.put("classRoomList",classRoomList);
            msg = "获取成功";
        }
        return new ResultDetial<>(msg, data);
    }
}
