package com.hd.clc.boya.service;

import com.hd.clc.boya.common.ResultDetial;
import com.hd.clc.boya.db.entity.User;
import com.hd.clc.boya.db.impl.UserMapper;
import com.hd.clc.boya.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService implements UserServiceImpl {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResultDetial query(Integer id) {
        Map<String, Object> data = new HashMap<>();
        User user = userMapper.queryById(id);
        data.put("user", user);
        return new ResultDetial(data);
    }
}