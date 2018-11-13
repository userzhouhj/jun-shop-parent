package com.jun.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.config.AlipayConfig;
import com.jun.base.BaseData;
import com.jun.dao.PaymentInfoDao;
import com.jun.domain.PaymentInfo;
import com.jun.utils.BaseDataUtil;
import com.jun.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class PayController implements IPayController {

    @Autowired
    private RedisTemplate tokenRedisTemplate;

    @Autowired
    private PaymentInfoDao paymentInfoDao;

    @Override
    public BaseData createToken(@RequestBody PaymentInfo paymentInfo) {

        //1.创建支付信息
        Integer resultNum = paymentInfoDao.savePaymentType(paymentInfo);
        if(resultNum <= 0){
            return BaseDataUtil.resultFail("订单创建失败");
        }
        //2.生成对应的token
        String payToken = TokenUtil.getPayToken();
        //3.将token存入redis
        tokenRedisTemplate.opsForValue().set(payToken,paymentInfo.getId().toString());
        tokenRedisTemplate.expire(payToken,60*15, TimeUnit.SECONDS);
        //4.返回对应的token

        JSONObject data = new JSONObject();
        data.put("payToken",payToken);
        return BaseDataUtil.resultSuccess(data);
    }

    @Override
    public BaseData findToken(@RequestParam("payToken") String token) {

        //1.判断参数的合法性
        if(token == null || token.equals("")){
            return BaseDataUtil.resultFail("令牌为空，不能进行支付");
        }
        //2.判断token是否过期
        String payToken = (String)tokenRedisTemplate.opsForValue().get(token);
        if(payToken == null || payToken.equals("")){
            return BaseDataUtil.resultFail("令牌过期请重新发起支付");
        }

        //3.从redis中取出token中的id
        Integer id = Integer.parseInt(payToken);
        //4.下单

        PaymentInfo paymentInfo = paymentInfoDao.getPaymentInfo(id);
        //5.对接支付宝进行返回数据
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id,
                AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key,
                AlipayConfig.sign_type);

        // 设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        // 商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = paymentInfo.getOrderId();
        // 付款金额，必填 企业金额
        String total_amount = paymentInfo.getPrice() + "";
        // 订单名称，必填
        String subject = "蚂蚁课堂充值会员";
        // 商品描述，可空
        // String body = new
        // String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\"," + "\"total_amount\":\"" + total_amount
                + "\"," + "\"subject\":\"" + subject + "\","
                // + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        // 若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
        // alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no
        // +"\","
        // + "\"total_amount\":\""+ total_amount +"\","
        // + "\"subject\":\""+ subject +"\","
        // + "\"body\":\""+ body +"\","
        // + "\"timeout_express\":\"10m\","
        // + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        // 请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

        // 请求
        try {
            String result = alipayClient.pageExecute(alipayRequest).getBody();
            JSONObject data = new JSONObject();
            data.put("payHtml", result);
            return BaseDataUtil.resultSuccess(data);
        } catch (Exception e) {
            return BaseDataUtil.resultFail("支付系统出故障了");
        }

    }
}
