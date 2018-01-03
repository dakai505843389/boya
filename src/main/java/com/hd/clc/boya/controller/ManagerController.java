package com.hd.clc.boya.controller;

import com.hd.clc.boya.common.Result;
import com.hd.clc.boya.service.IManagerService;
import com.hd.clc.boya.service.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/back/manager")
@CrossOrigin(value = "*", maxAge = 3600)
@RestController
public class ManagerController {
    @Autowired
    @Qualifier("managerServiceImpl")
    private IManagerService managerService;

    @Autowired
    @Qualifier("teacherServiceImpl")
    private ITeacherService teacherService;


    /** 注：改为add，添加权限验证，返回密码不能为md5加密后的值，增加参数（管理员类型）
     * 注册接口
     * @param account
     * @param password
     */
    @RequestMapping(value = "/addNewManager",method = RequestMethod.POST)
    public Result add(@RequestParam String account,
                      @RequestParam String password,
                      @RequestParam Integer managerType,
                      HttpServletRequest request)throws Exception{
        return  managerService.add(account,password,managerType,request);

    }

    /**
     * 登录接口
     * @param
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestParam String account,
                        @RequestParam String password,
                        HttpServletRequest request) {
        return  managerService.login(account, password, request);
    }

    /**
     * 新增教室接口
     * @param classRoom
     * @param maxNumber
     * @param request
     * @return
     */
    @RequestMapping(value = "/addNewClassRoom", method = RequestMethod.POST)
    public Result addNewClassRoom(@RequestParam String classRoom,
                                  @RequestParam Integer maxNumber,
                                  HttpServletRequest request){
        return managerService.addNewClassRoom(classRoom, maxNumber, request);
    }

    @RequestMapping(value = "/addNewClassType", method = RequestMethod.POST)
    public Result addNewClassType(@RequestParam String typeName, HttpServletRequest request){
        return managerService.addNewClassType(typeName, request);
    }

    /**
     * 教师审核通过接口
     * @param teacherId
     */
    @RequestMapping(value = "/allowTeacher",method = RequestMethod.POST)
    public Result allowTeacher(@RequestParam Integer teacherId){
        return  teacherService.allowTeacher(teacherId);
    }

    /**
     * 暂停教师资格接口
     * @param teacherId
     */
    @RequestMapping(value = "/suspendTeacher",method = RequestMethod.POST)
    public Result suspendTeacher(@RequestParam Integer teacherId){
        return  teacherService.suspendTeacher(teacherId);
    }

    /**
     * 交换课程排名
     * @param classTypeId1
     * @param classTypeId2
     * @return
     */
    @RequestMapping(value = "/changeClassTypeSortNum",method = RequestMethod.POST)
    public Result changeClassTypeSortNum(@RequestParam Integer classTypeId1,
                                        @RequestParam Integer classTypeId2){
        return  managerService.changeClassTypeSortNum(classTypeId1,classTypeId2);

    }
}
