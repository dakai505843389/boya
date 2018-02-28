package com.hd.clc.boya.service.impl;

import com.hd.clc.boya.db.entity.PaymentLog;
import com.hd.clc.boya.db.impl.PaymentLogMapper;
import com.hd.clc.boya.service.IPaymentLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PaymentLogServiceImpl implements IPaymentLogService {

    @Autowired
    private PaymentLogMapper paymentLogMapper;

    @Override
    public void logPayment(Integer paymentLogId, String description) {
        PaymentLog paymentLog = new PaymentLog();
        paymentLog.setPaymentId(paymentLogId);
        paymentLog.setDescription(description);
        paymentLog.setAddTime(new Date(System.currentTimeMillis()));
        paymentLogMapper.addNewPaymentLog(paymentLog);
    }
}
