package com.hd.clc.boya.db.impl;

import com.hd.clc.boya.db.entity.UserCollectionTeacherMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCollectionTeacherMapMapper {
    UserCollectionTeacherMap queryById(@Param("id") Integer id);
}
