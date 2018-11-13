package com.jun.feign;

import com.jun.controller.IOrderController;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient("shop-order")
@Component
public interface OrderControllerFeign extends IOrderController {
}
