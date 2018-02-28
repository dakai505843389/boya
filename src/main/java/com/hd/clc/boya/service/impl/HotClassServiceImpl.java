package com.hd.clc.boya.service.impl;

import com.hd.clc.boya.common.ResultDetial;
import com.hd.clc.boya.db.entity.HotClass;
import com.hd.clc.boya.db.impl.HotClassMapper;
import com.hd.clc.boya.service.IHotClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HotClassServiceImpl implements IHotClassService {

    @Autowired
    private HotClassMapper hotClassMapper;

    @Override
    public ResultDetial getHotClass() {
        Map<String, Object> data = new HashMap<>();
        List<HotClass> hotClassList = hotClassMapper.query();
        data.put("hotClassList", hotClassList);
        return new ResultDetial<>("查询成功", data);
    }



    /*------------------------------------------公共方法------------------------------------------*/


}
