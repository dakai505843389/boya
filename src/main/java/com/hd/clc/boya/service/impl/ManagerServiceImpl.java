package com.hd.clc.boya.service.impl;

import com.hd.clc.boya.common.MD5Util;
import com.hd.clc.boya.common.ResultDetial;
import com.hd.clc.boya.db.entity.ClassRoom;
import com.hd.clc.boya.db.entity.ClassType;
import com.hd.clc.boya.db.entity.Manager;
import com.hd.clc.boya.db.impl.ClassRoomMapper;
import com.hd.clc.boya.db.impl.ClassTypeMapper;
import com.hd.clc.boya.db.impl.ManagerMapper;
import com.hd.clc.boya.intercepter.MyIntercepter;
import com.hd.clc.boya.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ManagerServiceImpl implements IManagerService {

    @Autowired
    private ManagerMapper managerMapper;

    @Autowired
    private ClassRoomMapper classRoomMapper;

    @Autowired
    private ClassTypeMapper classTypeMapper;

    @Override
    public ResultDetial login(String account, String password, HttpServletRequest request) {
        Map<String, Object> data = new HashMap<>();
        String msg = null;
        Manager manager = managerMapper.queryByAccount(account);
        if (manager == null) {
            return new ResultDetial<>(-1, "没有该用户", data);

        } else if (manager.getPassword().equals(MD5Util.toMd5(password))) {
            manager.setLastLoginTime(new Date(System.currentTimeMillis()));
            if (managerMapper.updateMangerLogin(manager) < 1) {
                return new ResultDetial<>(-1, "登录失败！", data);
            } else {
                msg = "登录成功";
                manager.setPassword(password);
                MyIntercepter.loginSuccess(request, manager.getId());
            }

        } else {
            return new ResultDetial<>(-1, "密码错误", data);
        }
        data.put("manager", manager);
        return new ResultDetial<>(msg, data);
    }

    @Override
    public ResultDetial add(String account, String password ,Integer managerType,HttpServletRequest request
                                ) throws Exception {
        Map<String, Object> data = new HashMap<>();
        String msg;
        Manager manager = managerMapper.queryByAccount(account);
        if (verifySuperManager(request)) {
            if (manager != null) {
                return new ResultDetial<>(-1, "该管理员已存在", data);
            } else {
                manager = new Manager();
                manager.setAccount(account);
                manager.setPassword(MD5Util.toMd5(password));
                manager.setAddTime(new Date(System.currentTimeMillis()));
                manager.setManagerType(managerType);
                if (managerMapper.addNewManager(manager) < 1) {
                    return new ResultDetial<>(-1, "新建用户失败！", data);
                } else {
                    msg = "注册成功";
                    manager.setPassword(password);
                    data.put("manager", manager);
                }
            }
        }else {
            return new ResultDetial<>(-1, "非超管不能添加管理员！", data);
        }

        return new ResultDetial<>(msg, data);
    }

    @Override
    public ResultDetial addNewClassRoom(String classRoom, Integer maxNumber, HttpServletRequest request) {
        Map<String, Object> data = new HashMap<>();
        String msg;
        if (verifySuperManager(request)) {
            ClassRoom classRoomObject = new ClassRoom();
            classRoomObject.setClassRoom(classRoom);
            classRoomObject.setMaxNumber(maxNumber);
            classRoomObject.setAddTime(new Date(System.currentTimeMillis()));
            if (classRoomMapper.addNewClassRoom(classRoomObject) < 1) {
                return new ResultDetial<>(-1, "新增教室失败！", data);
            } else {
                msg = "新增教室成功！";
                data.put("classRoom", classRoomObject);
            }
        } else {
            return new ResultDetial<>(-1, "非超管不能新增教室！", data);
        }
        return new ResultDetial<>(msg, data);
    }

    @Override
    public ResultDetial addNewClassType(String typeName, HttpServletRequest request) {
        Map<String, Object> data = new HashMap<>();
        String msg;
        if (verifySuperManager(request)) {
            Integer sortNum = classTypeMapper.getMaxSortNum();
            if (sortNum == null){
                sortNum = 1;
            }else {
                sortNum += 1;
            }
            ClassType classType = new ClassType();
            classType.setTypeName(typeName);
            classType.setSortNum(sortNum);
            classType.setAddTime(new Date(System.currentTimeMillis()));
            if (classTypeMapper.addNewClassType(classType) < 1) {
                return new ResultDetial<>(-1, "新增课程类型失败！", data);
            } else {
                msg = "新增课程类型成功！";
                data.put("classType", classType);
            }
        } else {
            return new ResultDetial<>(-1, "非超管不能新增课程类型！", data);
        }
        return new ResultDetial<>(msg, data);
    }

    @Override
    @Transactional
    public ResultDetial changeClassTypeSortNum(Integer classTypeId1, Integer classTypeId2){
        Map<String, Object> data = new HashMap<>();
        String msg = "";
        ClassType classType1 = classTypeMapper.queryById(classTypeId1);
        ClassType classType2 = classTypeMapper.queryById(classTypeId2);
        if (classType1 != null){
            if (classType2 != null){
                if(classType1 != classType2){
                    Integer t = classType1.getSortNum();
                    classType1.setSortNum(classType2.getSortNum());
                    classType2.setSortNum(t);
                    if(classTypeMapper.changeClassTypeSortNum(classType1)<1){
                        if(classTypeMapper.changeClassTypeSortNum(classType2)<1){
                            return new ResultDetial<>(-1, "交换失败！", data);
                        }
                    }else {
                        msg = "交换排名成功";
                        data.put("classType1",classType1);
                        data.put("classType2",classType2);
                    }
                }else {
                    return new ResultDetial<>(-1, "输入id重复！", data);
                }


            }else {
                return new ResultDetial<>(-1, "输入id有误！", data);
            }

        }else {
            return new ResultDetial<>(-1, "输入id有误！", data);
        }
        return new ResultDetial<>(msg, data);
    }

    /*------------------------------------------公共方法------------------------------------------*/
    @Override
    public Manager getManagerById(Integer managerId) {
        return managerMapper.queryById(managerId);
    }

    /**
     * 验证当前登录者是否超管
     *
     * @param request
     * @return
     */
    private boolean verifySuperManager(HttpServletRequest request) {
        Manager manager = MyIntercepter.getManager(request);
        if (manager.getManagerType() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
