package com.jun.controller;

import com.alibaba.fastjson.JSONObject;
import com.jun.base.BaseData;
import com.jun.constants.BaseConstants;
import com.jun.feign.PayControllerFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;

@Controller
public class PCWebPayController {

    @Autowired
    private PayControllerFeign payControllerFeign;

    @RequestMapping("/pay")
    public void aliPay(@RequestParam("token") String token, HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=utf-8");
        if(StringUtils.isEmpty(token)){
            return ;
        }
        BaseData data = payControllerFeign.findToken(token);
        if(!data.getCode().equals(BaseConstants.RRTURN_SUCCESS_CODE)){
            String msg = data.getMsg();
            response.getWriter().println(msg);
            return ;
        }

        LinkedHashMap data1 = (LinkedHashMap) data.getData();
        String payHtml = (String)data1.get("payHtml");

        response.getWriter().println(payHtml);


    }
}
