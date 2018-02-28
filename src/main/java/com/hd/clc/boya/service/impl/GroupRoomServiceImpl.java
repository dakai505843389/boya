package com.hd.clc.boya.service.impl;

import com.hd.clc.boya.common.Result;
import com.hd.clc.boya.common.ResultDetial;
import com.hd.clc.boya.db.entity.Class;
import com.hd.clc.boya.db.entity.GroupRoom;
import com.hd.clc.boya.db.entity.UserSelectionClassMap;
import com.hd.clc.boya.db.impl.ClassMapper;
import com.hd.clc.boya.db.impl.GroupRoomMapper;
import com.hd.clc.boya.db.impl.UserSelectionClassMapMapper;
import com.hd.clc.boya.service.IGroupRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class GroupRoomServiceImpl implements IGroupRoomService {

    @Autowired
    private GroupRoomMapper groupRoomMapper;

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private UserSelectionClassMapMapper userSelectionClassMapMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result scanCodeForGroup(Integer userId, String sceneStr) {
        Map<String, Object> data = new HashMap<>();
        String msg = null;
        boolean rollback = false;
        GroupRoom groupRoom = groupRoomMapper.queryById(Integer.parseInt(sceneStr));
        if (groupRoom != null) {
            Integer classId = groupRoom.getClassId();
            Class classObject = classMapper.queryById(classId);
            //查询是否已经参与团购
            if (userSelectionClassMapMapper.queryForRepeated(userId, classId) < 1) {
                if (groupRoom.getStatus() == 0) {
                    UserSelectionClassMap userSelectionClassMap = new UserSelectionClassMap();
                    userSelectionClassMap.setUserId(userId);
                    userSelectionClassMap.setClassId(classId);
                    userSelectionClassMap.setTeacherId(classObject.getBelongTeacherId());
                    userSelectionClassMap.setIsGroup(1);
                    userSelectionClassMap.setGroupRoomId(groupRoom.getId());
                    userSelectionClassMap.setAddTime(new Date(System.currentTimeMillis()));
                    if (userSelectionClassMapMapper.creatNewMap(userSelectionClassMap) >= 1) {
                        if (groupRoomMapper.addNewNumber(groupRoom.getId()) >= 1){
                            if ((groupRoom.getCountNum() + 1) == groupRoom.getMaxNum()){
                                groupRoomMapper.modifyStatusForFull(groupRoom.getId());
                            }
                            msg = "加入团购成功！";
                            data.put("userSelectionClassMap", userSelectionClassMap);
                            data.put("groupRoom", groupRoom);
                        }else {
                            rollback = true;
                            msg = "选课失败！";
                        }
                    } else {
                        rollback = true;
                        msg = "选课失败！";
                    }
                } else {
                    rollback = true;
                    msg = "当前团购已结束或人满！";
                }
            } else {
                rollback = true;
                msg = "用户已有此课程订单！";
            }
        } else {
            rollback = true;
            msg = "不存在该团购！";
        }
        if (rollback) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResultDetial<>(-1, msg, data);
        } else {
            return new ResultDetial<>(msg, data);
        }
    }
}
