package com.hd.clc.boya.service.impl;

import com.hd.clc.boya.common.ResultDetial;
import com.hd.clc.boya.db.entity.Manager;
import com.hd.clc.boya.db.impl.ManagerMapper;
import com.hd.clc.boya.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ManagerServiceImpl implements IManagerService{
    @Autowired
    private ManagerMapper managerMapper;

    @Override
    public ResultDetial login(String code) {
        return  null;
    }

    @Override
    public ResultDetial register(String account,String password)throws Exception{
        Map<String, Object> data = new HashMap<>();
        String msg = null;
        Manager manager = managerMapper.queryByAccount(account);
        if(manager != null){
            return new ResultDetial(-1,"该管理员已存在",data);
        }else {
            manager.setAccount(account);
            manager.setPassword(password);
            msg="注册成功";
        }
        return new ResultDetial(msg, data);
    }
}
