package com.hd.clc.boya.db.impl;

import com.hd.clc.boya.db.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    User queryById(@Param("id") Integer id);
}
