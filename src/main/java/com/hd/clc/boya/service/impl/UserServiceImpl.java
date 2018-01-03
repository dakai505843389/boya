package com.hd.clc.boya.service.impl;

import com.hd.clc.boya.common.BaseVar;
import com.hd.clc.boya.common.FileUtil;
import com.hd.clc.boya.common.ResultDetial;
import com.hd.clc.boya.common.WxUtil;
import com.hd.clc.boya.db.entity.ClassType;
import com.hd.clc.boya.db.entity.HotClass;
import com.hd.clc.boya.db.entity.Teacher;
import com.hd.clc.boya.db.entity.User;
import com.hd.clc.boya.db.impl.ClassTypeMapper;
import com.hd.clc.boya.db.impl.HotClassMapper;
import com.hd.clc.boya.db.impl.TeacherMapper;
import com.hd.clc.boya.db.impl.UserMapper;
import com.hd.clc.boya.service.IUserService;
import com.hd.clc.boya.vo.UserCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HotClassMapper hotClassMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private ClassTypeMapper classTypeMapper;

    @Override
    public ResultDetial login(String code) {
        Map<String, Object> data = new HashMap<>();
        String msg = null;
        // 通过code与微信服务器通信，获取openid
        UserCode userCode = WxUtil.getUserCode(code);
        if (userCode == null){
            return new ResultDetial<>(-1, "code无效！", data);
        }
        User user = userMapper.queryByOpenid(userCode.getOpenid());
        // 获取首页热门课程列表
        List<HotClass> hotClassList = hotClassMapper.query();
        //获取课程类型列表
        List<ClassType> classTypeList = classTypeMapper.list();
        // 若不存在，则为第一次登录，新建用户数据，将openid存入数据库
        if (user == null){
            user = new User();
            user.setOpenid(userCode.getOpenid());
            // 判断unionid是否为空
            if (userCode.getUnionid() != null){
                user.setUnionid(userCode.getUnionid());
            }
            user.setAddTime(new Date(System.currentTimeMillis()));
            user.setLastLoginTime(new Date(System.currentTimeMillis()));
            // 新建用户
            if (userMapper.addNewUser(user) < 1){
                return new ResultDetial<>(-1, "新建用户失败！", data);
            } else {
                msg = "登录成功！用户为第一次登录";
            }
        } else { // 若存在，则更新最后登录时间
            user.setLoginTimes(user.getLoginTimes() + 1);
            user.setLastLoginTime(new Date(System.currentTimeMillis()));
            if (userMapper.updateUserLogin(user) < 1){
                return new ResultDetial<>(-1, "登录失败！", data);
            } else {
                msg = "登录成功！";
            }
        }
        data.put("user", user);
        data.put("classTypeList",classTypeList);
        data.put("hotClassList", hotClassList);
        return new ResultDetial<>(msg, data);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultDetial modifyUser(Integer userId, String userName, MultipartFile image, Integer sex, String tel) throws Exception {
        Map<String, Object> data = new HashMap<>();
        String msg = null;
        User user = userMapper.queryById(userId);
        if (userName != null){
            user.setUserName(userName);
        }
        if (image != null){
            String imagePath = BaseVar.USER_IMAGE_PATH + userId + "/";
            String fileName = FileUtil.upload4Stream(image.getInputStream(), imagePath, image.getOriginalFilename());
            imagePath += fileName;
            user.setImagePath(imagePath);
        }
        if (sex != null){
            user.setSex(sex);
        }
        if (tel != null){
            user.setTel(tel);
        }
        if (userMapper.updateUserInformation(user) < 1){
            return new ResultDetial(-1, "更新用户信息失败！", data);
        } else {
            data.put("user", user);
            msg = "更新成功！";
        }
        return new ResultDetial(msg, data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultDetial applyTeacher(Integer userId, String description, Integer teacherType) {
        Map<String, Object> data = new HashMap<>();
        String msg = "";
        Teacher teacher = teacherMapper.queryByUserId(userId);
        if (teacher != null){
            Integer status = teacher.getStatus();
            if (status == 0){
                msg = "教师资格已申请，正在审核中！";
            } else if (status == 1){
                msg = "用户已是教师，无需再次申请！";
            } else if (status == 2){
                msg = "教师资格被暂停，请联系相关人员！";
            }
        } else {
            teacher = new Teacher();
            teacher.setUserId(userId);
            teacher.setDescription(description);
            teacher.setTeacherType(teacherType);
            teacher.setAddTime(new Date(System.currentTimeMillis()));
            if (teacherMapper.addNewTeacher(teacher) < 1){
                return new ResultDetial(-1, "申请教师资格失败", data);
            } else {
                msg = "申请已经提交，正在审核中！";
                data.put("teacher", teacher);
            }
        }
        return new ResultDetial(msg, data);
    }


    /*------------------------------------------公共方法------------------------------------------*/


}
