package com.hd.clc.boya.service.impl;

import com.hd.clc.boya.common.Result;
import com.hd.clc.boya.common.ResultDetial;
import com.hd.clc.boya.db.entity.Class;
import com.hd.clc.boya.db.entity.GroupRoom;
import com.hd.clc.boya.db.entity.User;
import com.hd.clc.boya.db.entity.UserSelectionClassMap;
import com.hd.clc.boya.db.impl.ClassMapper;
import com.hd.clc.boya.db.impl.GroupRoomMapper;
import com.hd.clc.boya.db.impl.UserMapper;
import com.hd.clc.boya.db.impl.UserSelectionClassMapMapper;
import com.hd.clc.boya.service.IGroupRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

@Service
public class GroupRoomServiceImpl implements IGroupRoomService {

    @Autowired
    private GroupRoomMapper groupRoomMapper;

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private UserSelectionClassMapMapper userSelectionClassMapMapper;

    @Autowired
    private UserMapper userMapper;

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
                            data.put("class", classObject);
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
                UserSelectionClassMap userSelectionClassMap = userSelectionClassMapMapper.queryByUserIdAndClassId(userId, classId);
                rollback = true;
                msg = "用户已有此课程订单！";
                data.put("class", classObject);
                data.put("userSelectionClassMap", userSelectionClassMap);
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

    @Override
    public Result queryForAllGroupNumber(int userId, int classId) {
        Map<String, Object> data = new HashMap<>();
        String msg;
        UserSelectionClassMap userSelectionClassMap = userSelectionClassMapMapper.queryByUserIdAndClassId(userId, classId);
        if (userSelectionClassMap != null){
            if(userSelectionClassMap.getIsGroup() == 1){
                GroupRoom groupRoom = groupRoomMapper.queryById(userSelectionClassMap.getGroupRoomId());
                List<UserSelectionClassMap> mapList = userSelectionClassMapMapper.queryListByGroup(groupRoom.getId());
                List<User> userList = new ArrayList<>();
                for (int i = 0; i < mapList.size(); i++){
                    UserSelectionClassMap otherUserSelectionClassMap = mapList.get(i);
                    User user = userMapper.queryById(otherUserSelectionClassMap.getUserId());
                    userList.add(user);
                }
                data.put("groupRoom", groupRoom);
                data.put("userList", userList);
                msg = "查询成功";
            }else {
                msg = "用户非团购！";
            }
        }else {
            msg = "无此订单";
        }
        return new ResultDetial<>(msg, data);
    }

    @Override
    public Result getCode(int userId, int classId) {
        Map<String, Object> data = new HashMap<>();
        String msg;
        UserSelectionClassMap userSelectionClassMap = userSelectionClassMapMapper.queryByUserIdAndClassId(userId, classId);
        if (userSelectionClassMap != null){
            if(userSelectionClassMap.getIsGroup() == 1){
                GroupRoom groupRoom = groupRoomMapper.queryById(userSelectionClassMap.getGroupRoomId());
                data.put("code", groupRoom.getWxacode());
                msg = "查询成功";
            }else {
                msg = "用户非团购！";
            }
        }else {
            msg = "无此订单";
        }
        return new ResultDetial<>(msg, data);
    }

    @Override
    public Result query(int groupRoomId) {
        Map<String, Object> data = new HashMap<>();
        String msg;
        GroupRoom groupRoom = groupRoomMapper.queryById(groupRoomId);
        List<UserSelectionClassMap> mapList = userSelectionClassMapMapper.queryListByGroup(groupRoom.getId());
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < mapList.size(); i++){
            UserSelectionClassMap otherUserSelectionClassMap = mapList.get(i);
            User user = userMapper.queryById(otherUserSelectionClassMap.getUserId());
            userList.add(user);
        }
        data.put("groupRoom", groupRoom);
        data.put("userList", userList);
        msg = "查询成功";
        return new ResultDetial<>(msg, data);
    }
}
