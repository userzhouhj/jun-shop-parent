package com.jun.controller;

import com.jun.base.BaseData;
import com.jun.constants.TokenConstant;
import com.jun.feign.MemberControllerFeign;
import com.jun.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;

@Controller
@Slf4j
public class PCWebIndexController {

    @Autowired
    private MemberControllerFeign memberControllerFeign;

    @GetMapping("/")
    public String index(HttpServletRequest request){

        //1.获取cookie
        String memberToken = CookieUtil.getCookieValue(request,"memberToken");
        //System.out.println(memberToken);
        if(memberToken.equals("") || memberToken == null){
            return "index";
        }
        log.info(memberToken);
        //3.调用会员接口从缓存中查找对应的键值对
        BaseData baseData = memberControllerFeign.loginByToken(memberToken);
        LinkedHashMap data = (LinkedHashMap) baseData.getData();
        String username = (String)data.get("username");
        //4.根据缓存信息去数据库中找出对应是用户并加入到request域中
        request.setAttribute("username",username);

        return "index";
    }
}
