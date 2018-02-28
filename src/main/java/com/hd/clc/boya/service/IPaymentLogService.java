package com.hd.clc.boya.service;

import org.springframework.stereotype.Service;

@Service
public interface IPaymentLogService {
    public void logPayment(Integer paymentLogId, String description);
}
