package com.jun.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.AlipaySignature;
import com.config.AlipayConfig;
import com.jun.base.BaseData;
import com.jun.constants.BaseConstants;
import com.jun.dao.PaymentInfoDao;
import com.jun.domain.PaymentInfo;
import com.jun.feign.OrderControllerFeign;
import com.jun.utils.BaseDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PayCallBackController implements IPayCallBackController {

    @Autowired
    private PaymentInfoDao paymentInfoDao;

    @Autowired
    private OrderControllerFeign orderControllerFeign;

    @Override
    public BaseData synCallBack(@RequestParam Map<String,String> map) throws Exception{
        boolean signVerified = AlipaySignature.rsaCheckV1(map, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——
        if(!signVerified) {
            return BaseDataUtil.resultSuccess("验签失败");
        }
        //商户订单号
        String outTradeNo= map.get("out_trade_no");
        //支付宝交易号
        String tradeNo = map.get("trade_no");
        //付款金额
        String totalAmount = map.get("total_amount");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("outTradeNo",outTradeNo);
        jsonObject.put("tradeNo",tradeNo);
        jsonObject.put("totalAmount",totalAmount);

        return BaseDataUtil.resultSuccess(jsonObject);

    }

    @Override
    public String asynCallBack(@RequestParam Map<String,String> map) throws Exception{

        boolean signVerified = AlipaySignature.rsaCheckV1(map, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        if(signVerified) {//验证成功
            //商户订单号
            String outTradeNo = map.get("out_trade_no");
            PaymentInfo byOrderIdPayInfo = paymentInfoDao.getByOrderIdPayInfo(outTradeNo);
            if (byOrderIdPayInfo == null) {
                return "fail";
            }

            if (byOrderIdPayInfo.getState().equals(1)) {
                return "success";
            }
            //支付宝交易号
            String tradeNo = map.get("trade_no");

            //交易状态
            String tradeStatus = map.get("trade_status");

            byOrderIdPayInfo.setState(1);// 标识为已经支付
            byOrderIdPayInfo.setPayMessage("success");
            byOrderIdPayInfo.setPlatformorderId(tradeNo);
            // 手动 begin begin
            Integer updateResult = paymentInfoDao.updatePayInfo(byOrderIdPayInfo);
            if (updateResult <= 0) {
                return "fail";
            }

//            BaseData orderResult = orderControllerFeign.update(1l, tradeNo, outTradeNo);
//            if (!orderResult.getCode().equals(BaseConstants.RRTURN_SUCCESS_CODE)) {
//                // 回滚 手动回滚 rollback
//                return "fail";
//            } // 2PC 3PC TCC MQ
//            // 手动 提交 comiit;
            return "success";
        }
        return "fail";
    }
}
