package com.jun.feign;

import com.jun.controller.IPayController;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient("shop-payService")
@Component
public interface PayControllerFeign extends IPayController {
}
