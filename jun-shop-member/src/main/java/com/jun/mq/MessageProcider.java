package com.jun.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;

@Component
public class MessageProcider {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void sendMsgToMq(Destination destination, String json){
        jmsMessagingTemplate.convertAndSend(destination,json);

    }

}
