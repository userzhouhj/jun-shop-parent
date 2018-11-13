package com.jun.adapter;

import com.alibaba.fastjson.JSONObject;

/**
 * 定义同一消息发送接口
 */
public interface MessageAdapter {

    public void sendMsg(JSONObject body);

}
