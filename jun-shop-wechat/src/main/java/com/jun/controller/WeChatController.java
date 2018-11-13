package com.jun.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;


@RestController
@Slf4j
public class WeChatController {


    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code,@RequestParam("state") String state){

        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxf9192066845a5d07&secret=debc8ac1e607ccf8367d63a3d6bb7c7d&code="+code+"&grant_type=authorization_code";

        RestTemplate restTemplate1 = new RestTemplate();
        String object = restTemplate1.getForObject(url, String.class);

        JSONObject parse = (JSONObject) JSONObject.parse(object);

        String accessToken = (String)parse.get("access_token");
        String openId = (String) parse.get("openid");

        String url2 = "https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken+"&openid="+openId+"&lang=zh_CN";
        RestTemplate restTemplate2 = new RestTemplate();
        String object2 = restTemplate2.getForObject(url2, String.class);
        JSONObject parse2 = (JSONObject) JSONObject.parse(object2);

        String nickname = (String)parse2.get("nickname");
        String headimgurl = (String)parse2.get("headimgurl");

        System.out.println("用户姓名： "+nickname);
        System.out.println("用户头像地址： "+headimgurl);

    }


}
