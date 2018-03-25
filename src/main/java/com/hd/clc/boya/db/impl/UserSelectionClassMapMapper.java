package com.hd.clc.boya.db.impl;

import com.hd.clc.boya.db.entity.User;
import com.hd.clc.boya.db.entity.UserSelectionClassMap;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSelectionClassMapMapper {
    UserSelectionClassMap queryById(@Param("id") Integer id);
    int queryForRepeated(@Param("userId") Integer userId, @Param("classId") Integer classId);
    int creatNewMap(UserSelectionClassMap userSelectionClassMap);
    int paySuccess(UserSelectionClassMap userSelectionClassMap);
    List<UserSelectionClassMap> getList(@Param("classId") int classId);
    int classBegin(@Param("classId") int classId);
    List<UserSelectionClassMap> getListByGroupId(@Param("groupRoomId") int groupRoomId);
    int shutdown(UserSelectionClassMap userSelectionClassMap);
    int evaluted(@Param("id") int id);
    UserSelectionClassMap queryByUserIdAndClassId(@Param("userId") Integer userId, @Param("classId") Integer classId);
    int updatePaymentId(@Param("id") int id, @Param("paymentId") int paymentId);
}
