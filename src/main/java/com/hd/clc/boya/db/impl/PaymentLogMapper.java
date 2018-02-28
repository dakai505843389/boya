package com.hd.clc.boya.db.impl;

import com.hd.clc.boya.db.entity.PaymentLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentLogMapper {
    PaymentLog queryById(@Param("id") int id);
    int deleteById(@Param("id") int id);
    int addNewPaymentLog(PaymentLog paymentLog);
}
