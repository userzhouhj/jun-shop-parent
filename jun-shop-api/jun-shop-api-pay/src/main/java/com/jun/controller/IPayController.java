package com.jun.controller;


import com.jun.base.BaseData;
import com.jun.domain.PaymentInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/pay")
public interface IPayController {

    //创建支付令牌
    @RequestMapping("/createToken")
    public BaseData createToken(@RequestBody PaymentInfo paymentInfo);


    //通过令牌查找订单信息
    @RequestMapping("/findToken")
    public BaseData findToken(@RequestParam("payToken") String token);
}
