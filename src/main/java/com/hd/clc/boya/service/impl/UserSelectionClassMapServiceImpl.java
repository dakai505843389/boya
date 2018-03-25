package com.hd.clc.boya.service.impl;

import com.hd.clc.boya.common.Result;
import com.hd.clc.boya.common.ResultDetial;
import com.hd.clc.boya.db.entity.UserSelectionClassMap;
import com.hd.clc.boya.db.impl.UserSelectionClassMapMapper;
import com.hd.clc.boya.service.IUserSelectionClassMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserSelectionClassMapServiceImpl implements IUserSelectionClassMapService {

    @Autowired
    private UserSelectionClassMapMapper userSelectionClassMapMapper;

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
}
