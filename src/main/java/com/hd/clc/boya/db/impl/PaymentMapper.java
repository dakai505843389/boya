package com.hd.clc.boya.db.impl;

import com.hd.clc.boya.db.entity.Payment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMapper {
    Payment queryById(@Param("id") Integer id);
    int addNewPayment(Payment payment);
    int paySuccess(Payment payment);
}
