package com.hd.clc.boya.service;

import com.hd.clc.boya.db.entity.ManagerSession;
import org.springframework.stereotype.Repository;

@Repository
public interface IManagerSessionService {
    boolean add(ManagerSession managerSession);
    ManagerSession get(String SID);
}
