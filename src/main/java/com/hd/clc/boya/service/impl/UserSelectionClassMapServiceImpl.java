package com.hd.clc.boya.service.impl;

import com.hd.clc.boya.common.Result;
import com.hd.clc.boya.common.ResultDetial;
import com.hd.clc.boya.db.entity.Class;
import com.hd.clc.boya.db.entity.UserSelectionClassMap;
import com.hd.clc.boya.db.impl.ClassMapper;
import com.hd.clc.boya.db.impl.UserSelectionClassMapMapper;
import com.hd.clc.boya.service.IUserSelectionClassMapService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserSelectionClassMapServiceImpl implements IUserSelectionClassMapService {

    @Autowired
    private UserSelectionClassMapMapper userSelectionClassMapMapper;

    @Autowired
    private ClassMapper classMapper;

    @Override
    public Result query(int userId, int classId) {
        Map<String, Object> data = new HashMap<>();
        String msg;
        UserSelectionClassMap userSelectionClassMap = userSelectionClassMapMapper.queryByUserIdAndClassId(userId, classId);
        if (userSelectionClassMap != null){
            msg = "查询成功";
            data.put("userSelectionClassMap", userSelectionClassMap);
        }else {
            msg = "无此订单（映射）";
        }
        return new ResultDetial<>(msg, data);
    }

    @Override
    public Result queryList(int userId, int isEnd) {
        Map<String, Object> data = new HashMap<>();
        List<UserSelectionClassMap> list = userSelectionClassMapMapper.queryList(userId, isEnd);
        List<Class> classList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++){
            UserSelectionClassMap userSelectionClassMap = list.get(i);
            Class classObject = classMapper.queryById(userSelectionClassMap.getClassId());
            classList.add(classObject);
        }
        data.put("mapList", list);
        data.put("classList", classList);
        return new ResultDetial<>("查询成功！", data);
    }
}
