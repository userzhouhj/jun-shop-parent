package com.jun.controller;


import com.jun.base.TextMessage;
import com.jun.utils.CheckUtil;
import com.jun.utils.XmlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

@RestController
@Slf4j
public class DispatcherController {

    @GetMapping("/dispatcherServlet")
    public String dispatcher(String signature,String timestamp,String nonce,String echostr){
        boolean falg = CheckUtil.checkSignature(signature, timestamp, nonce);
        if(falg){
            return echostr;
        }
        return null;
    }

    @PostMapping("/dispatcherServlet")
    public void dispatcher(HttpServletRequest request, HttpServletResponse response) throws Exception{
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("utf-8");

        log.info("##############微信消息推送#########");
        //解析xml
        Map<String, String> resultMap = XmlUtils.parseXml(request);
        log.info(resultMap.toString());
        String MsgType = resultMap.get("MsgType");
        switch (MsgType){
            case "text":
                String toUserName = resultMap.get("ToUserName");
                String fromUserName = resultMap.get("FromUserName");
                String content = resultMap.get("Content");

                String msg = null;

                if(content.equals("程序员")){
                    msg = setMsg("我是程序员",fromUserName,toUserName);
                }
                log.info(msg);
                response.getWriter().println(msg);
                break;
            default:
                break;
        }

    }

    public String setMsg(String content,String toUsertoUserName,String fromUserName){
        TextMessage textMessage = new TextMessage();
        textMessage.setFromUserName(fromUserName);
        textMessage.setToUserName(toUsertoUserName);
        textMessage.setContext(content);
        textMessage.setMsgType("text");
        textMessage.setCreateTime(new Date().getTime());

        String returnMsg = XmlUtils.messageToXml(textMessage);
        return returnMsg;
    }

    
}
