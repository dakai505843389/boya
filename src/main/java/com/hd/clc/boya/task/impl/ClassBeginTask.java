package com.hd.clc.boya.task.impl;

import com.hd.clc.boya.db.entity.Class;
import com.hd.clc.boya.db.impl.ClassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

@Component
@Scope("prototype")
public class ClassBeginTask extends TimerTask {

    @Autowired
    private ClassMapper classMapper;

    private Integer classId;

    public ClassBeginTask(){
        this.classId = classId;
    }

    @Override
    public void run() {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date(System.currentTimeMillis())) + "检测课程是否开启:" + classId);
        Class classObject = classMapper.queryById(classId);
        System.out.println(classObject.getClassName());
    }
}
