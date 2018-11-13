package com.jun.controller;

import com.jun.base.BaseData;
import com.jun.dao.OrderDao;
import com.jun.utils.BaseDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController implements IOrderController {
    @Autowired
    private OrderDao orderDao;

    @Override
    public BaseData update(@RequestParam("isPay") Long isPay, @RequestParam("payId") String aliPayId,
                           @RequestParam("orderNumber") String orderNumber) {

        int result = orderDao.updateOrder(isPay, aliPayId, orderNumber);

        if(result<=0){
            return BaseDataUtil.resultFail("订单修改异常");
        }

        return BaseDataUtil.resultSuccess();


    }
}
