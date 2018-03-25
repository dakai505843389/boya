package com.hd.clc.boya.db.impl;

import com.hd.clc.boya.db.entity.PaySign;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaySignMapper {
    PaySign query(@Param("id")int id);
    int add(PaySign paySign);
}
