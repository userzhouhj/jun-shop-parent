package com.jun.controller;

import com.jun.base.BaseData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/callback")
public interface IPayCallBackController {

    @GetMapping("/syncallback")
    public BaseData synCallBack(@RequestParam Map<String,String> map) throws Exception;//同步回调

    @PostMapping("/asyncallback")
    public String asynCallBack(@RequestParam Map<String,String> map)throws Exception;//异步回调
}


