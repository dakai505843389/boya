package com.hd.clc.boya.db.impl;

import com.hd.clc.boya.db.entity.Payment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMapper {
    Payment queryById(@Param("id") Integer id);
    int addNewPayment(Payment payment);
    int paySuccess(Payment payment);
    int checkForRepeated(@Param("selectionMapId")int selectionMapId);
    int updateSignId(Payment payment);
    Payment queryByMapId(@Param("selectionMapId")int selectionMapId);
    int updateout_trade_no(Payment payment);
}
