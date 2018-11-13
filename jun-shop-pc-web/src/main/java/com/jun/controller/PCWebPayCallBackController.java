package com.jun.controller;

import com.jun.base.BaseData;
import com.jun.constants.BaseConstants;
import com.jun.feign.PayCallBackControllerFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/pay")
@Slf4j
public class PCWebPayCallBackController {

    @Autowired
    private PayCallBackControllerFeign payCallBackControllerFeign;

    @GetMapping("/syncallback")
    public String synCallBack(HttpServletRequest request) throws Exception{

        //响应请求
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        //处理请求并调用接口
        BaseData baseData = payCallBackControllerFeign.synCallBack(params);
        LinkedHashMap data = (LinkedHashMap) baseData.getData();
        if(!baseData.getCode().equals(BaseConstants.RRTURN_SUCCESS_CODE)){
            return "error";
        }

        String outTradeNo = (String)data.get("outTradeNo");
        String tradeNo = (String)data.get("tradeNo");
        String totalAmount = (String)data.get("totalAmount");

        request.setAttribute("outTradeNo",outTradeNo);
        request.setAttribute("tradeNo",tradeNo);
        request.setAttribute("totalAmount",totalAmount);

        return "pay_success";

    }

    @PostMapping("/asyncallback")
    @ResponseBody
    public String asyncallback(HttpServletRequest request)throws Exception{
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
//			// 乱码解决，这段代码在出现乱码时使用
//			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        log.info("###支付宝异步回调CallBackController####synCallBack开始 params:{}", params);
        String result = payCallBackControllerFeign.asynCallBack(params);
        return result;
    }

}
