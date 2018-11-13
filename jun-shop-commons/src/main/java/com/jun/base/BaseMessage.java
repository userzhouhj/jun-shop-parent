package com.jun.base;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseMessage {

    private String ToUserName;
    private String FromUserName;
    private Long CreateTime;
    private String MsgType;
    //private String Content;
}
