package com.hd.clc.boya.service.impl;

import com.hd.clc.boya.common.*;
import com.hd.clc.boya.db.entity.*;
import com.hd.clc.boya.db.entity.Class;
import com.hd.clc.boya.db.impl.*;
import com.hd.clc.boya.service.IGroupRoomService;
import com.hd.clc.boya.service.IPaymentLogService;
import com.hd.clc.boya.service.IPaymentService;
import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentServiceImpl implements IPaymentService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserSelectionClassMapMapper userSelectionClassMapMapper;

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private GroupRoomMapper groupRoomMapper;

    @Qualifier("paymentLogServiceImpl")
    @Autowired
    private IPaymentLogService paymentLogService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result getPaySign(Integer userId, Integer selectionMapId, HttpServletRequest request) {
        Map<String, Object> data = new HashMap<>();
        boolean rollback = false;
        String msg = null;
        User user = userMapper.queryById(userId);
        if (user != null){
            UserSelectionClassMap userSelectionClassMap = userSelectionClassMapMapper.queryById(selectionMapId);
            if (userSelectionClassMap != null){
                if (userSelectionClassMap.getIsPaid() != 1){
                    Class classObject = classMapper.queryById(userSelectionClassMap.getClassId());
                    if (classObject.getStatus() == 2 || classObject.getStatus() == 3){
                        Payment payment = new Payment();
                        payment.setUserId(userId);
                        payment.setTeacherId(userSelectionClassMap.getTeacherId());
                        payment.setClassId(userSelectionClassMap.getClassId());
                        payment.setSelectionMapId(selectionMapId);
                        if (userSelectionClassMap.getIsPaid() == 1){
                            payment.setPrice(classObject.getGroupPrice());
                        }else {
                            payment.setPrice(classObject.getSinglePrice());
                        }
                        payment.setAddTime(new Date(System.currentTimeMillis()));
                        if (paymentMapper.addNewPayment(payment) >= 1){
                            paymentLogService.logPayment(payment.getId(), "开启支付，等待获取微信服务器签名");
                            try {
                                Map<String, Object> sign = WxUtil.getWxPaySign(user.getOpenid(), payment.getId(), payment.getPrice(), request);
                                data.put("sign", sign);
                                data.put("paymentId", payment.getId());
                                paymentLogService.logPayment(payment.getId(), "调用微信统一下单接口成功，等待支付结果通知");
                                msg = "下单成功！";
                            } catch (Exception e){
                                msg = "调用微信统一下单接口失败！";
                                rollback = true;
                            }
                        }else {
                            msg = "开启支付失败！";
                            rollback = true;
                        }
                    }else {
                        msg = "该课程已结束或被暂停！";
                        rollback = true;
                    }
                }else {
                    msg = "该课程已支付！";
                    rollback = true;
                }
            }else {
                msg = "不存在该选课信息！";
                rollback = true;
            }
        }else {
            msg = "不存在该用户！";
            rollback = true;
        }
        if (rollback){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResultDetial<>(-1, msg, data);
        }else {
            return new ResultDetial<>(msg, data);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) {
        Integer paymentId = null;
        boolean rollback = false;
        String decription = null;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            //sb为微信返回的xml
            String notityXml = sb.toString();
            String resXml = "";
            System.out.println("接收到的报文：" + notityXml);
            Map map = XmlUtil.doXMLParse(notityXml);
            String returnCode = (String) map.get("return_code");
            if ("SUCCESS".equals(returnCode)) {
                //验证签名是否正确
                if (StringUtil.verify(StringUtil.createLinkString(map), (String) map.get("sign"), WxUtil.getApi_key(), "utf-8")) {
                    /**支付成功后操作begin**/
                    //支付订单id
                    paymentId = (Integer) map.get("out_trade_no");
                    //支付价格
                    Integer price = (Integer) map.get("cash_fee");
                    //支付时间
                    String time_end = (String) map.get("time_end");
                    SimpleDateFormat formatter = new SimpleDateFormat( "yyyyMMddHHmmss");
                    //获取格式化支付时间
                    Date payTime = formatter.parse(time_end);
                    //获取支付订单实体
                    Payment payment = paymentMapper.queryById(paymentId);
                    //判断支付价格是否正确
                    if (payment.getPrice() == price){
                        //设置支付状态为成功
                        payment.setStatus(1);
                        payment.setPayTime(payTime);
                        payment.setUpdateTime(new Date(System.currentTimeMillis()));
                        if (paymentMapper.paySuccess(payment) < 1){
                            rollback = true;
                            decription = "支付出错！";
                        }else {
                            //获取选课映射实体
                            UserSelectionClassMap userSelectionClassMap = userSelectionClassMapMapper.queryById(payment.getSelectionMapId());
                            userSelectionClassMap.setUpdateTime(new Date(System.currentTimeMillis()));
                            if (userSelectionClassMapMapper.paySuccess(userSelectionClassMap) < 1){
                                rollback = true;
                                decription = "支付出错！";
                            }else {
                                Class classObject = classMapper.queryById(payment.getClassId());
                                //判断是否团购
                                if (userSelectionClassMap.getIsGroup() == 1){
                                    int groupRoomId = userSelectionClassMap.getGroupRoomId();
                                    GroupRoom groupRoom = groupRoomMapper.queryById(groupRoomId);
                                    int paidNum = groupRoom.getPaidNum() + 1;
                                    int maxNum = groupRoom.getMaxNum();
                                    //判断团购已支付人数是否已满
                                    if (paidNum == maxNum){
                                        classObject.setPaidAmount(classObject.getPaidAmount() + paidNum * price);
                                        groupRoomMapper.modifyStatusForPaid(paidNum, groupRoomId);
                                        if (classMapper.modifyPaidAmount(classObject) < 1) {
                                            rollback = true;
                                            decription = "支付出错！";
                                        } else {
                                            decription = "支付成功！";
                                        }
                                    }else {
                                        if (groupRoomMapper.modifyPaidNum(paidNum, groupRoomId) < 1){
                                            rollback = true;
                                            decription = "支付出错！";
                                        } else {
                                            decription = "支付成功！";
                                        }
                                    }
                                }else {
                                    classObject.setPaidAmount(classObject.getPaidAmount() + price);
                                    if (classMapper.modifyPaidAmount(classObject) < 1) {
                                        rollback = true;
                                        decription = "支付出错！";
                                    } else {
                                        decription = "支付成功！";
                                    }
                                }
                            }
                        }
                    }else {
                        rollback = true;
                        decription = "支付金额不符！";
                    }
                    /**支付成功后操作end**/
                    //通知微信服务器已经支付成功
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                }
            } else {
                rollback = true;
                decription = "微信服务器未能成功响应";
            }
            if (rollback){
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            paymentLogService.logPayment(paymentId, decription);
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();
        } catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            paymentLogService.logPayment(paymentId, "接受支付推送发生错误");
        }
    }
}
