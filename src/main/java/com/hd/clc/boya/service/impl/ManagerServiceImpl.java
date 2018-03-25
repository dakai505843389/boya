package com.hd.clc.boya.service.impl;

import com.hd.clc.boya.common.MD5Util;
import com.hd.clc.boya.common.ResultDetial;
import com.hd.clc.boya.db.entity.*;
import com.hd.clc.boya.db.entity.Class;
import com.hd.clc.boya.db.impl.*;
import com.hd.clc.boya.intercepter.MyIntercepter;
import com.hd.clc.boya.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class ManagerServiceImpl implements IManagerService {

    @Autowired
    private ManagerMapper managerMapper;

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private ClassTypeMapper classTypeMapper;

    @Autowired
    private HotClassMapper hotClassMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private ClassServiceImpl classServiceImpl;

    @Autowired
    private ClassLogServiceImpl classLogServiceImpl;

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
    public ResultDetial add(String account, String password ,Integer managerType,HttpServletRequest request) throws Exception {
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
    public ResultDetial addNewClassType(String typeName, String imagePath, HttpServletRequest request) {
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
            classType.setImagePath(imagePath);
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

    @Override
    public ResultDetial getAllowingClass(HttpServletRequest request) {
        Map<String, Object> data = new HashMap<>();
        List<Class> classList = classMapper.getAllowingClass();
        if (classList != null && classList.size() != 0){
            data.put("classList", classList);
            return new ResultDetial("查询成功！", data);
        } else {
            return new ResultDetial(-1, "查询失败！", data);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultDetial allowClass(Integer classId) {
        Map<String, Object> data = new HashMap<>();
        Class classObject = classMapper.queryById(classId);
        Teacher teacher = teacherMapper.queryById(classObject.getBelongTeacherId());
        if (classObject != null){
            if (classObject.getStatus() == 0){
                if (classMapper.updateStatus(classId, 2) < 1){
                    return new ResultDetial(-1, "审核修改失败！", data);
                }else {
                    int maxHotNum = hotClassMapper.getMaxSortNum();
                    HotClass hotClass = new HotClass();
                    hotClass.setClassId(classId);
                    hotClass.setClassName(classObject.getClassName());
                    hotClass.setClassRoom(classObject.getClassRoom());
                    hotClass.setClassBeginTime(classObject.getClassBeginTime());
                    hotClass.setIsAllowGroup(classObject.getIsAllowGroup());
                    hotClass.setClassImagePath(classObject.getClassImagePath());
                    hotClass.setTeacherType(teacher.getTeacherType());
                    hotClass.setSortNum(maxHotNum + 1);
                    hotClass.setAddTime(new Date(System.currentTimeMillis()));
                    if (hotClassMapper.addNewHotClass(hotClass) < 1){
                        return new ResultDetial(-1, "审核修改失败！", data);
                    }else {
                        Date classBeginTime = classObject.getClassBeginTime();
                        //开启定时任务
                        Timer timer = new Timer();
                        //在课程开始三分钟前检测未完成支付的人数以及未完成的团购
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Class classBeginObject = classMapper.queryById(classId);
                                classServiceImpl.checkGroup(classObject);
                            }
                        }, new Date(classBeginTime.getTime() - 180000));
                        //在课程开始前检测人数是否达到最低限制，并开始上课
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Class classBeginObject = classMapper.queryById(classId);
                                int status = classBeginObject.getStatus();
                                if (status == 2 || status == 3) {
                                    if (classBeginObject.getCountNumber() >= classBeginObject.getNumberLimit()) {
                                        classLogServiceImpl.log(classId, "满足最低上课人数，开始上课！");
                                        classServiceImpl.beginClassMethod(classObject);
                                    } else {
                                        classLogServiceImpl.log(classId, "未满足最低上课人数要求！");
                                        classServiceImpl.shutClassmethod(classObject);
                                    }
                                }else {
                                    if (status == 0){
                                        classLogServiceImpl.log(classId, "课程未通过审核！");
                                    }else if (status == 1){
                                        classLogServiceImpl.log(classId, "课程被暂停！");
                                    }else if (status == 4){
                                        classLogServiceImpl.log(classId, "课程已开始！");
                                    }else if (status == 5){
                                        classLogServiceImpl.log(classId, "课程已停止！");
                                    }
                                }
                            }
                        }, classBeginTime);
                        return new ResultDetial("审核通过！", data);
                    }
                }
            }else {
                return new ResultDetial(-1, "该课程不处于待审核状态！", data);
            }
        }else {
            return new ResultDetial(-1,"不存在该课程！", data);
        }
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
