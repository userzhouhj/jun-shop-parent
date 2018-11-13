package com.jun.feign;

import com.jun.controller.IMemberController;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

@FeignClient("shop-member")
@Component
public interface MemberControllerFeign extends IMemberController {
}
