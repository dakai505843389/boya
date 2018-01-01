package com.hd.clc.boya.intercepter;

import com.hd.clc.boya.common.BaseVar;
import com.hd.clc.boya.common.NumberUtil;
import com.hd.clc.boya.common.StringUtil;
import com.hd.clc.boya.db.entity.Manager;
import com.hd.clc.boya.db.entity.ManagerSession;
import com.hd.clc.boya.service.IManagerService;
import com.hd.clc.boya.service.impl.ManagerServiceImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class MyIntercepter implements HandlerInterceptor {

    private static final Map<String, ManagerSession> sessionMap = new HashMap<>();

    private static IManagerService managerService;

    public void setManagerService(ManagerServiceImpl managerService){
        MyIntercepter.managerService = managerService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String sid = (String) request.getSession().getAttribute("sid");
        if (StringUtil.isNull(sid)){
            request.setAttribute("code", -1);
            request.setAttribute("msg", "请先登录！");
            return false;
        }
        ManagerSession managerSession = sessionMap.get(sid);
        if (managerSession == null){
            request.setAttribute("code", -1);
            request.setAttribute("msg", "登录过期！");
            return false;
        }
        if (System.currentTimeMillis() > managerSession.getOutTime().getTime()){
            loginOut(request);
            request.setAttribute("code", -1);
            request.setAttribute("msg", "登录过期！");
            return false;
        }
        managerSession.setOutTime(new Date(System.currentTimeMillis() + BaseVar.SESSION_OUTTIME));
        sessionMap.put(sid, managerSession);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }

    public static void loginSuccess(HttpServletRequest request, Integer managerId){
        String sid = System.currentTimeMillis() + "" + NumberUtil.createNum(6);
        ManagerSession managerSession = new ManagerSession();
        managerSession.setSID(sid);
        managerSession.setManagerId(managerId);
        managerSession.setStatus(0);
        managerSession.setAddTime(new Date(System.currentTimeMillis()));
        managerSession.setOutTime(new Date(System.currentTimeMillis() + BaseVar.SESSION_OUTTIME));
        sessionMap.put(sid, managerSession);
        request.getSession().setAttribute("sid", sid);
    }

    public static void loginOut(HttpServletRequest request){
        String sid = (String) request.getSession().getAttribute("sid");
        request.getSession().removeAttribute("sid");
        if (!StringUtil.isNull(sid)){
            sessionMap.remove(sid);
        }
    }

    public static Manager getManager(HttpServletRequest request){
        Integer managerId = getManagerId(request);
        return managerService.getManagerById(managerId);
    }

    public static Integer getManagerId(HttpServletRequest request){
        String sid = (String) request.getSession().getAttribute("sid");
        ManagerSession managerSession = sessionMap.get(sid);
        return managerSession.getManagerId();
    }

}
