package com.hd.clc.boya.service;

import com.hd.clc.boya.common.Result;
import com.hd.clc.boya.common.ResultDetial;
import com.hd.clc.boya.db.entity.Manager;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface IManagerService {
    ResultDetial add(String account,String password,Integer managerType,HttpServletRequest request)throws Exception;
    ResultDetial login(String account, String password, HttpServletRequest request);
    ResultDetial addNewClassRoom(String classRoom, Integer maxNumber, HttpServletRequest request);
    ResultDetial addNewClassType(String typeName, HttpServletRequest request);
    ResultDetial changeClassTypeSortNum(Integer classTypeId1, Integer classTypeId2);
    Manager getManagerById(Integer managerId);

}
