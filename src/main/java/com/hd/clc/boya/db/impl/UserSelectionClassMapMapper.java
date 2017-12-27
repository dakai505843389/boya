package com.hd.clc.boya.db.impl;

import com.hd.clc.boya.db.entity.UserSelectionClassMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSelectionClassMapMapper {
    UserSelectionClassMap queryById(@Param("id") Integer id);
}
