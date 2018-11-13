package com.jun.controller;


import com.alibaba.fastjson.JSONObject;
import com.jun.base.BaseData;
import com.jun.domain.User;
import com.jun.feign.MemberControllerFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;

@Controller
public class PCWebLoginController {

    @Autowired
    private MemberControllerFeign memberControllerFeign;

    @PostMapping("/login")
    public String login(User user, HttpServletRequest request, HttpServletResponse response){
        //1.参数验证
        //2.查看缓存是否有相关用户的 token
        //3.调用登录接口进行登录获取token
        BaseData baseData = memberControllerFeign.login(user);
        //4.登录失败返回登录界面
        if(baseData.getCode().equals(500)){
            request.setAttribute("error","登录失败");
            return "index";
        }
        //5.登录成功进行token缓存到cookie
        LinkedHashMap data = (LinkedHashMap) baseData.getData();
        Cookie cookie = new Cookie("memberToken",data.get("memberToken").toString());
        cookie.setPath("/");
        cookie.setMaxAge(60*60*24*90);
        response.addCookie(cookie);
        //6.进入成功界面（首页或者其他页）
        return "redirect:/";
    }

}
