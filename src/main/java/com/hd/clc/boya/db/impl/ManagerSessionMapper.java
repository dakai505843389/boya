package com.hd.clc.boya.db.impl;

import com.hd.clc.boya.db.entity.ManagerSession;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerSessionMapper {
    int add(ManagerSession managerSession);
    ManagerSession queryBySID(@Param("SID") String SID);
    int deleteBySID(@Param("SID") String SID);
}
