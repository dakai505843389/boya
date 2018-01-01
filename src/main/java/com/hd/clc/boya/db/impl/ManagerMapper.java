package com.hd.clc.boya.db.impl;

import com.hd.clc.boya.db.entity.Manager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerMapper {
    Manager queryById(@Param("id") Integer id);
    Manager queryByAccount(@Param("account") String  account);
    int addNewManager(Manager manager);
    int updateMangerLogin(Manager manager);
}
