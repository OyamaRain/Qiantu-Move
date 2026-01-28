package com.hotaru.controller.user;

import com.hotaru.context.BaseContext;
import com.hotaru.dto.user.OrderCommentDTO;
import com.hotaru.dto.user.OrderDTO;
import com.hotaru.dto.user.OrderPageQueryDTO;
import com.hotaru.result.PageResult;
import com.hotaru.result.Result;
import com.hotaru.service.user.UserOrderService;
import com.hotaru.vo.user.OrderVO;
import com.hotaru.vo.user.UserOrderDetailVO;
import com.hotaru.vo.user.UserOrderMoverVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/pay/{orderId}")
    @Tag(name = "订单功能接口")
    @Operation(summary = "支付订单")
    public Result<OrderVO> payOrder(@PathVariable("orderId") Long orderId){
        log.info("用户支付订单：{}",orderId);
        orderService.payOrder(orderId);
        return Result.success();
    }

    @GetMapping("/list")
    @Tag(name = "订单功能接口")
    @Operation(summary = "查询订单列表")
    public Result<PageResult> page(@ParameterObject OrderPageQueryDTO orderPageQueryDTO){
        Long currentUserId = BaseContext.getCurrentId();
        orderPageQueryDTO.setUserId(currentUserId);
        log.info("用户查询订单列表：{}",orderPageQueryDTO);
        PageResult pageResult = orderService.pageQuery(orderPageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/{orderId}")
    @Tag(name = "订单功能接口")
    @Operation(summary = "根据ID查询订单详情")
    public Result<UserOrderDetailVO> getDetails(@PathVariable("orderId") Long orderId){
        log.info("用户查询订单详情：{}",orderId);
        UserOrderDetailVO userOrderDetailVO = orderService.getDetailsById(orderId);
        return Result.success(userOrderDetailVO);
    }

    @PutMapping("/cancel/{orderId}")
    @Tag(name = "订单功能接口")
    @Operation(summary = "取消订单")
    public Result cancelOrder(@PathVariable("orderId") Long orderId){
        log.info("用户取消订单：{}", orderId);
        orderService.cancelOrder(orderId);
        return Result.success();
    }

    @GetMapping("/{orderId}/comment/info")
    @Tag(name = "订单功能接口")
    @Operation(summary = "根据订单ID查询师傅信息")
    public Result<UserOrderMoverVO> getCommentInfo(@PathVariable("orderId") Long orderId){
        Long currentUserId = BaseContext.getCurrentId();
        log.info("用户查询订单师傅信息，userId={}, orderId={}", currentUserId, orderId);
        UserOrderMoverVO userOrderMoverVO = orderService.getMoverInfo(orderId);
        return Result.success(userOrderMoverVO);
    }

    @PostMapping("/comment")
    @Tag(name = "订单功能接口")
    @Operation(summary = "评价订单")
    public Result comment(@RequestBody OrderCommentDTO orderCommentDTO){
        Long currentUserId = BaseContext.getCurrentId();
        log.info("用户评价订单，userId={}, comment={}", currentUserId, orderCommentDTO);
        orderService.commentOrder(orderCommentDTO);
        return Result.success();
    }


}
