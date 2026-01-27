package com.hotaru.controller.user;

import com.hotaru.dto.user.OrderDTO;
import com.hotaru.result.Result;
import com.hotaru.service.user.UserOrderService;
import com.hotaru.vo.user.OrderVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/orders")
@Slf4j
public class UserOrderController {

    @Autowired
    private UserOrderService orderService;

    @PostMapping("/submit")
    @Tag(name = "订单功能接口")
    @Operation(summary = "用户下单")
    public Result<OrderVO> createOrder(@RequestBody OrderDTO orderDTO){
        log.info("用户下单：{}",orderDTO);
        OrderVO orderVO = orderService.createOrder(orderDTO);
        return Result.success(orderVO);
    }
}
