package com.hd.clc.boya.task.impl;

import com.hd.clc.boya.task.ITask;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class MyTask implements ITask {

    @Scheduled(cron = "0 0/10 * * * ? ")   //五分钟执行一次
    @Override
    public void checkClassBegin() {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date(System.currentTimeMillis())) +"定时任务，每十分钟执行一次！");
    }
}
