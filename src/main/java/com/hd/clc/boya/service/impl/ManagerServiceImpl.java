package com.hd.clc.boya.service.impl;

import com.hd.clc.boya.common.MD5Util;
import com.hd.clc.boya.common.ResultDetial;
import com.hd.clc.boya.db.entity.Manager;
import com.hd.clc.boya.db.impl.ManagerMapper;
import com.hd.clc.boya.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ManagerServiceImpl implements IManagerService{
    @Autowired
    private ManagerMapper managerMapper;

    @Override
    public ResultDetial login(String account,String password) {
        Map<String, Object> data = new HashMap<>();
        String msg = null;
        Manager manager = managerMapper.queryByAccount(account);
        if(manager == null){
            return new ResultDetial<>(-1,"没有该用户",data);

        }else if(manager.getPassword().equals(MD5Util.toMd5(password))){
            manager.setLastLoginTime(new Date(System.currentTimeMillis()));
            if (managerMapper.updateMangerLogin(manager)<1){
                return new ResultDetial<>(-1, "登录失败！", data);
            }else {
                msg="登录成功";
            }

        }else {
            return new ResultDetial<>(-1,"密码错误",data);
        }
        data.put("manager",manager);
        return new ResultDetial<>(msg, data);
    }

    @Override
    public ResultDetial register(String account,String password)throws Exception{
        Map<String, Object> data = new HashMap<>();
        String msg;
        Manager manager = managerMapper.queryByAccount(account);
        if(manager != null){
            return new ResultDetial<>(-1,"该管理员已存在",data);
        }else {
            manager = new Manager();
            manager.setAccount(account);
            manager.setPassword(MD5Util.toMd5(password));
            manager.setAddTime(new Date(System.currentTimeMillis()));

            if(managerMapper.addNewManager(manager)<1){
                return new ResultDetial<>(-1, "新建用户失败！", data);
            }else {
                msg="注册成功";
                data.put("manaher",manager);
            }
        }

        return new ResultDetial<>(msg, data);
    }
}
