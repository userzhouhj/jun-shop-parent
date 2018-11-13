package com.jun.controller;

import com.jun.base.BaseData;
import com.jun.domain.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/member")
public interface IMemberController {

    @RequestMapping("/get/{id}")
    public BaseData<User> get(@PathVariable("id") Integer id);

    @RequestMapping("/register")
    public BaseData<String> register(@RequestBody User user);

    @RequestMapping("/login")
    public BaseData login(@RequestBody User user);

    @RequestMapping("/loginfromtoken")
    public BaseData loginByToken(@RequestParam("token") String token);


}
