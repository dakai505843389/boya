package com.hd.clc.boya.db.impl;

import com.hd.clc.boya.db.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    User queryById(@Param("id") Integer id);
    User queryByOpenid(@Param("openid") String openid);
    int addNewUser(User user);
    int updateUserInformation(User user);
    int updateUserLogin(User user);
    int updateUserType(User user);
}
