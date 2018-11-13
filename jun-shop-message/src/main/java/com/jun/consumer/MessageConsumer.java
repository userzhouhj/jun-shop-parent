package com.jun.consumer;

import com.alibaba.fastjson.JSONObject;
import com.jun.adapter.MessageAdapter;
import com.jun.constants.MessageConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * 消息发送消费者
 */
@Component
@Slf4j
public class MessageConsumer {

    private MessageAdapter messageAdapter;
    @Autowired
    private MessageAdapter mailMessage;

    @JmsListener(destination = "message")
    public void send(String json){
        log.info("#############消息服务推送############");
        if(json.isEmpty()){
            return ;
        }
        //获取协定的消息报文，进行解析解析接口类型，本类处理电子邮件
        JSONObject root = JSONObject.parseObject(json);
        JSONObject header = root.getJSONObject(MessageConstant.MESSAGE_HEADER);
        String interfaceType = header.getString(MessageConstant.MESSAGE_INTERFACETYPE);

        if(interfaceType.isEmpty()){
            return ;
        }
        if(interfaceType.equals(MessageConstant.MESSAGE_INTERFACETYPE_EMAIL)){
            messageAdapter = mailMessage;
        }
        if(messageAdapter == null){
            return ;
        }
        //将消息体进行发送，实现类进行处理
        JSONObject content = root.getJSONObject(MessageConstant.MESSAGE_CONTENT);
        messageAdapter.sendMsg(content);
    }

}
