package com.hd.clc.boya.db.impl;

import com.hd.clc.boya.db.entity.ClassRoom;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRoomMapper {
    ClassRoom queryById(@Param("id") Integer id);
    int addNewClassRoom(ClassRoom classRoom);
    List<ClassRoom> list();
}
