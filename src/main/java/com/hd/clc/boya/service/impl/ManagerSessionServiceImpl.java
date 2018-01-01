package com.hd.clc.boya.service.impl;

import com.hd.clc.boya.db.entity.ManagerSession;
import com.hd.clc.boya.db.impl.ManagerSessionMapper;
import com.hd.clc.boya.service.IManagerSessionService;
import org.springframework.beans.factory.annotation.Autowired;

public class ManagerSessionServiceImpl implements IManagerSessionService {

    @Autowired
    private ManagerSessionMapper managerSessionMapper;

    @Override
    public boolean add(ManagerSession managerSession) {
        if (managerSessionMapper.add(managerSession) < 1 ) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public ManagerSession get(String SID) {
        return managerSessionMapper.queryBySID(SID);
    }

}
