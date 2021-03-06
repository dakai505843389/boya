package com.hd.clc.boya.service.impl;

import com.hd.clc.boya.common.*;
import com.hd.clc.boya.db.entity.*;
import com.hd.clc.boya.db.entity.Class;
import com.hd.clc.boya.db.impl.*;
import com.hd.clc.boya.service.IClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClassServiceImpl implements IClassService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private UserSelectionClassMapMapper userSelectionClassMapMapper;

    @Autowired
    private GroupRoomMapper groupRoomMapper;

    @Autowired
    private ClassLogServiceImpl classLogServiceImpl;

    @Autowired
    private HotClassMapper hotClassMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result releaseClass(Integer teacherId, String className, String description,
                               String classImage, String classRoom, Integer maxNumber,
                               Integer numberLimit, Integer singlePrice,
                               Integer isAllowGroup, Integer groupPrice, Integer groupNumberLimit,
                               Integer classTypeId, Date classBeginTime,
                               Date classEndTime) throws Exception {
        Map<String, Object> data = new HashMap<>();
        String msg;
        Teacher teacher = teacherMapper.queryById(teacherId);
        if (teacher.getStatus() != 1) {
            msg = "非正常状态教师不可发布课程！";
            return new ResultDetial<>(-1, msg, data);
        } else {
            Class classObject = new Class();
            classObject.setBelongTeacherId(teacherId);
            classObject.setClassName(className);
            classObject.setDescription(description);
            classObject.setTeacherName(teacher.getName());
            classObject.setTeacherImagePath(teacher.getImagePath());
            classObject.setClassImagePath(classImage);
            classObject.setClassRoom(classRoom);
            classObject.setMaxNumber(maxNumber);
            classObject.setNumberLimit(numberLimit);
            if (singlePrice <= 0) {
                msg = "单价需大于0元！";
                return new ResultDetial<>(-1, msg, data);
            }
            classObject.setSinglePrice(singlePrice);
            classObject.setIsAllowGroup(isAllowGroup);
            if (1 == isAllowGroup) {
                if (groupPrice != null) {
                    if (groupPrice <= 0) {
                        msg = "团购价格需大于0元！";
                        return new ResultDetial<>(-1, msg, data);
                    }
                    classObject.setGroupPrice(groupPrice);
                } else {
                    classObject.setGroupPrice(singlePrice);
                }
                if (groupNumberLimit != null) {
                    classObject.setGroupNumberLimit(groupNumberLimit);
                } else {
                    classObject.setGroupNumberLimit(1);
                }
            }
            classObject.setClassTypeId(classTypeId);
            classObject.setClassBeginTime(classBeginTime);
            classObject.setClassEndTime(classEndTime);
            classObject.setClassAddTime(new Date(System.currentTimeMillis()));
            if (classMapper.addNewClass(classObject) < 1) {
                msg = "发布课程失败！";
                return new ResultDetial<>(-1, msg, data);
            } else {
                msg = "发布成功，进入待审核阶段，请等待审核！";
                data.put("class", classObject);
            }
        }
        return new ResultDetial<>(msg, data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result chooseClass(Integer userId, Integer classId, Integer isGroup, HttpServletRequest request) {
        Map<String, Object> data = new HashMap<>();
        String msg;
        if (userSelectionClassMapMapper.queryForRepeated(userId, classId) < 1) {
            Class classObject = classMapper.queryById(classId);
            if (classObject.getStatus() == 2) {
                UserSelectionClassMap userSelectionClassMap = new UserSelectionClassMap();
                userSelectionClassMap.setUserId(userId);
                userSelectionClassMap.setClassId(classId);
                userSelectionClassMap.setTeacherId(classObject.getBelongTeacherId());
                userSelectionClassMap.setIsGroup(isGroup);
                if (isGroup == 1) {
                    if (classObject.getIsAllowGroup() == 1) {
                        classObject.setCountNumber(classObject.getCountNumber() + classObject.getGroupNumberLimit());
                        if (classObject.getCountNumber() <= classObject.getMaxNumber()) {
                            GroupRoom groupRoom = new GroupRoom();
                            groupRoom.setOrganizerId(userId);
                            groupRoom.setClassId(classId);
                            groupRoom.setCountNum(1);
                            groupRoom.setMaxNum(classObject.getMaxNumber());
                            groupRoom.setAddTime(new Date(System.currentTimeMillis()));
                            String path = request.getSession().getServletContext().getRealPath("/");
                            groupRoomMapper.createNewGroup(groupRoom);
                            String pathName = "upload/qrCode/" + groupRoom.getId().toString() + ".png";
                            if (WxUtil.getminiqrQr(groupRoom.getId().toString(), BaseVar.USER_GROUP_WXACODE_PATH, path + pathName)) {
                                groupRoom.setWxacode(pathName);
                                groupRoomMapper.updateWxacode(groupRoom.getId(), pathName);
                                //if (groupRoomMapper.createNewGroup(groupRoom) < 1) {
                                //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                //return new ResultDetial<>(-1, "创建订单失败！", data);
                                //} else {
                                userSelectionClassMap.setGroupRoomId(groupRoom.getId());
                                userSelectionClassMap.setAddTime(new Date(System.currentTimeMillis()));
                                if (userSelectionClassMapMapper.creatNewMap(userSelectionClassMap) < 1) {
                                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                    return new ResultDetial<>(-1, "创建订单失败！", data);
                                }
                                if (classObject.getCountNumber() == classObject.getMaxNumber()) {
                                    classObject.setStatus(3);
                                }
                                classMapper.updateNumber(classObject);
                                data.put("groupRoom", groupRoom);
                                msg = "团购已开启，请在开课半小时前完成支付！";
                                //}
                            } else {
                                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                                return new ResultDetial<>(-1, "微信二维码生成失败！", data);
                            }
                        } else {
                            msg = "课程剩余可选人数超出团购人数！";
                        }
                    } else {
                        msg = "该课程不允许团购或团购时间截止！";
                    }
                } else {
                    userSelectionClassMap.setAddTime(new Date(System.currentTimeMillis()));
                    if (userSelectionClassMapMapper.creatNewMap(userSelectionClassMap) < 1) {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                        return new ResultDetial<>(-1, "创建订单失败！", data);
                    } else {
                        classObject.setCountNumber(classObject.getCountNumber() + 1);
                        if (classObject.getCountNumber() == classObject.getMaxNumber()) {
                            classObject.setStatus(3);
                        }
                        classMapper.updateNumber(classObject);
                        msg = "创建订单成功，请在开课半小时前完成支付！";
                    }
                }
                data.put("userSelectionClassMap", userSelectionClassMap);
            } else {
                msg = "课程人数已满或已结束！";
            }
        } else {
            msg = "用户已有此课程订单！";
            return new ResultDetial<>(-1, msg, data);
        }
        return new ResultDetial<>(msg, data);
    }

    @Override
    public Result beginClass(Integer teacherId, Integer classId) {
        Map<String, Object> data = new HashMap<>();
        String msg;
        Class classObject = classMapper.queryById(classId);
        int status = classObject.getStatus();
        if (status == 2 || status == 3) {
            checkGroup(classObject);
            beginClassMethod(classObject);
            msg = "课程开始！";
        } else if (status == 0) {
            classLogServiceImpl.log(classId, "课程未通过审核！");
            msg = "课程未通过审核！";
        } else if (status == 1) {
            classLogServiceImpl.log(classId, "课程被暂停！");
            msg = "课程被暂停！";
        } else if (status == 4) {
            classLogServiceImpl.log(classId, "课程已开始！");
            msg = "课程已开始！";
        } else if (status == 5) {
            classLogServiceImpl.log(classId, "课程已停止！");
            msg = "课程已停止！";
        } else {
            msg = "课程不能正常开始！";
        }
        return new ResultDetial<>(msg, data);
    }

    @Override
    public Result queryById(int classId) {
        Map<String, Object> data = new HashMap<>();
        String msg;
        Class classObject = classMapper.queryById(classId);
        data.put("class", classObject);
        msg = "查询课程成功！";
        return new ResultDetial<>(msg, data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result revokeClass(int userId, int selectionMapId) {
        Map<String, Object> data = new HashMap<>();
        String msg = null;
        boolean rollback = false;
        try {
            UserSelectionClassMap userSelectionClassMap = userSelectionClassMapMapper.queryById(selectionMapId);
            // 判断是否存在该订单
            if (userSelectionClassMap != null) {
                Class classObject = classMapper.queryById(userSelectionClassMap.getClassId());
                if (classObject.getStatus() == 3 || classObject.getStatus() == 2) {
                    if(classObject.getStatus() == 3){
                        classObject.setStatus(2);
                    }
                    classObject.setCountNumber(classObject.getCountNumber() - 1);
                    classMapper.updateNumber(classObject);
                    // 判断状态是否为未上课
                    if (userSelectionClassMap.getStatus() == 0) {
                        // 判断是否团购
                        if (userSelectionClassMap.getIsGroup() == 0) {
                            GroupRoom groupRoom = groupRoomMapper.queryById(userSelectionClassMap.getGroupRoomId());
                            // 判断订单是否已经支付
                            if (userSelectionClassMap.getIsPaid() == 0) {
                                // 若团购人数已满，则修改状态为未满
                                if (groupRoom.getStatus() == 1) {
                                    groupRoom.setStatus(0);
                                }
                                // 更新团购房间状态
                                groupRoom.setCountNum(groupRoom.getCountNum() - 1);
                                groupRoom.setUpdateTime(new Date(System.currentTimeMillis()));
                                groupRoomMapper.updateCountNum(groupRoom);
                                // 更新个人订单状态为退课
                                userSelectionClassMap.setStatus(2);
                                userSelectionClassMap.setUpdateTime(new Date(System.currentTimeMillis()));
                                userSelectionClassMapMapper.updateStatus(userSelectionClassMap);
                                msg = "退课成功！";
                                // 订单已支付
                            } else {
                                if (groupRoom.getStatus() == 2) {
                                    msg = "该次团购已完成，所有人已付款，不能退课！";
                                    rollback = true;
                                } else if (groupRoom.getStatus() == 1) {
                                    groupRoom.setStatus(0);
                                }
                                // 更新团购状态
                                groupRoom.setCountNum(groupRoom.getCountNum() - 1);
                                groupRoom.setPaidNum(groupRoom.getPaidNum() - 1);
                                groupRoom.setUpdateTime(new Date(System.currentTimeMillis()));
                                groupRoomMapper.updateCountNum(groupRoom);
                                // 更新个人订单状态
                                userSelectionClassMap.setStatus(2);
                                userSelectionClassMap.setIsPaid(2);
                                userSelectionClassMap.setUpdateTime(new Date(System.currentTimeMillis()));
                                userSelectionClassMapMapper.updateStatus(userSelectionClassMap);
                                // 更新用户余额
                                User user = userMapper.queryById(userId);
                                user.setAccountBalance(user.getAccountBalance() + classObject.getGroupPrice());
                                userMapper.updateAccountBalance(user);
                                msg = "退课成功！";
                            }
                            // 若不是团购
                        } else {
                            if (userSelectionClassMap.getIsPaid() == 0) {
                                // 更新个人订单状态为退课
                                userSelectionClassMap.setStatus(2);
                                userSelectionClassMap.setUpdateTime(new Date(System.currentTimeMillis()));
                                userSelectionClassMapMapper.updateStatus(userSelectionClassMap);
                                msg = "退课成功！";
                            } else {
                                // 更新个人订单状态
                                userSelectionClassMap.setStatus(2);
                                userSelectionClassMap.setIsPaid(2);
                                userSelectionClassMap.setUpdateTime(new Date(System.currentTimeMillis()));
                                userSelectionClassMapMapper.updateStatus(userSelectionClassMap);
                                // 更新用户余额
                                User user = userMapper.queryById(userId);
                                user.setAccountBalance(user.getAccountBalance() + classObject.getSinglePrice());
                                userMapper.updateAccountBalance(user);
                            }
                        }
                    } else {
                        msg = "已上课或已退课";
                        rollback = true;
                    }
                }else {
                    msg = "该课程已结束或被暂停！";
                    rollback = true;
                }
            } else {
                msg = "无该选课信息！";
                rollback = true;
            }
        } catch (Exception e) {
            rollback = true;
            if (msg == null){
                msg = "退课失败！";
            }
        }
        if (rollback) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResultDetial<>(-1, msg, data);
        }
        return new ResultDetial<>(msg, data);
    }


    /**
     * ---------------------------------------公共方法---------------------------------------------
     **/

    @Transactional(rollbackFor = Exception.class)
    public boolean revokeClassByMapId(int mapId) throws Exception{
        return false;
    }

    /**
     * 开课
     *
     * @param classObject
     */
    @Transactional(rollbackFor = Exception.class)
    public void beginClassMethod(Class classObject) {
        hotClassMapper.classBegin(classObject.getId());
        userSelectionClassMapMapper.classBegin(classObject.getId());
        classMapper.classBegin(classObject.getId());
        int teacherId = classObject.getBelongTeacherId();
        Teacher teacher = teacherMapper.queryById(teacherId);
        User user = userMapper.queryById(teacher.getId());
        user.setAccountBalance(user.getAccountBalance() + classObject.getPaidAmount());
        userMapper.updateAccountBalance(user);
    }

    /**
     * 不开课
     *
     * @param classObject
     */
    @Transactional(rollbackFor = Exception.class)
    public void shutClassmethod(Class classObject) {
        hotClassMapper.classBegin(classObject.getId());
        classMapper.shutClass(classObject.getId());
        List<UserSelectionClassMap> list = userSelectionClassMapMapper.getList(classObject.getId());
        for (int i = 0; i < list.size(); i++) {
            User user = userMapper.queryById(list.get(i).getUserId());
            user.setAccountBalance(user.getAccountBalance() + classObject.getPaidAmount());
            userMapper.updateAccountBalance(user);
        }
    }

    /**
     * 检测团购
     */
    @Transactional(rollbackFor = Exception.class)
    public void checkGroup(Class classObject) {
        classMapper.shutGroup(classObject.getId());
        List<GroupRoom> groupRoomList = groupRoomMapper.getList(classObject.getId());
        //遍历每个未完成的团购
        for (int i = 0; i < groupRoomList.size(); i++) {
            //将团购房间设置为停止
            groupRoomMapper.modifyStatusForshut(groupRoomList.get(i).getId());
            //遍历每个参与此次团购的用户选课映射
            List<UserSelectionClassMap> mapList = userSelectionClassMapMapper.getListByGroupId(groupRoomList.get(i).getId());
            for (int j = 0; j < mapList.size(); j++) {
                UserSelectionClassMap map = mapList.get(j);
                //修改状态为退课
                map.setStatus(2);
                //如果已付款，则退款
                if (map.getIsPaid() == 1) {
                    map.setIsPaid(2);
                    User user = userMapper.queryById(mapList.get(j).getUserId());
                    user.setAccountBalance(user.getAccountBalance() + classObject.getPaidAmount());
                    userMapper.updateAccountBalance(user);
                }
                userSelectionClassMapMapper.shutdown(map);
            }
        }
    }
}
