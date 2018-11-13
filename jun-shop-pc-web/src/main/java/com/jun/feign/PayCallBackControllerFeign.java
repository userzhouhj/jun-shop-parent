package com.jun.feign;

import com.jun.controller.IPayCallBackController;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient("shop-payService")
@Component
public interface PayCallBackControllerFeign extends IPayCallBackController {
}
