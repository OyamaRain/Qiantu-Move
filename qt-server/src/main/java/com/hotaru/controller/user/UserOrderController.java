package com.hotaru.controller.user;

import com.hotaru.dto.user.OrderDTO;
import com.hotaru.result.Result;
import com.hotaru.vo.user.OrderVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/orders")
@Slf4j
public class UserOrderController {


    @PostMapping("/submit")
    @Tag(name = "订单管理")
    @Operation(summary = "创建订单")
    public Result<OrderVO> createOrder(@RequestBody OrderDTO orderDTO){
        log.info("创建订单：{}",orderDTO);
        return Result.success();
    }
}
