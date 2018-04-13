package com.hd.clc.boya.db.impl;

import com.hd.clc.boya.db.entity.GroupRoom;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRoomMapper {
    GroupRoom queryById(@Param("id") Integer id);
    int deleteById(@Param("id") Integer id);
    int createNewGroup(GroupRoom groupRoom);
    int updateWxacode(@Param("id") Integer id, @Param("wxacode") String wxacode);
    int modifyStatusForPaid(@Param("paidNum") Integer paidNum, @Param("id") Integer id);
    int modifyPaidNum(@Param("paidNum") Integer paidNum, @Param("id") Integer id);
    int addNewNumber(@Param("id") Integer id);
    int modifyStatusForFull(@Param("id") Integer id);
    List<GroupRoom> getList(@Param("classId") int id);
    int modifyStatusForshut(@Param("id") Integer id);
    int updateCountNum(GroupRoom groupRoom);
}
