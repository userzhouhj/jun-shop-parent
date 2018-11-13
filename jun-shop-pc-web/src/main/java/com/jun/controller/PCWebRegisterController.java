package com.jun.controller;

import com.jun.base.BaseData;
import com.jun.domain.User;
import com.jun.feign.MemberControllerFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PCWebRegisterController {

    @Autowired
    private MemberControllerFeign memberControllerFeign;

    @PostMapping("/register")
    public String register(User user){
        //1.验证参数
        //2.调用接口
        BaseData<String> baseData = memberControllerFeign.register(user);
        //3.错误处理
        if(!baseData.getCode().equals(200)){
            return "register";
        }

        //4.成功返回
        return "login";
    }



}
