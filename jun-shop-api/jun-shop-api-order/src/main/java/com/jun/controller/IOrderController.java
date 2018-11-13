package com.jun.controller;

import com.jun.base.BaseData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/order")
public interface IOrderController {
    @RequestMapping("/update")
    public BaseData update(@RequestParam("isPay") Long isPay, @RequestParam("payId") String aliPayId,
                           @RequestParam("orderNumber") String orderNumber);
}
