package com.hotaru.controller.mover;

import com.hotaru.dto.mover.CompletedPageQueryDTO;
import com.hotaru.dto.mover.OngoingPageQueryDTO;
import com.hotaru.dto.mover.OrderPageQueryDTO;
import com.hotaru.result.PageResult;
import com.hotaru.result.Result;
import com.hotaru.service.mover.MoverOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mover/order")
@Slf4j
public class MoverOrderController {

    @Autowired
    private MoverOrderService moverOrderService;

    @GetMapping("/list/pending")
    @Tag(name = "订单管理")
    @Operation(summary = "查看待接订单")
    public Result<PageResult> getPendingOrders(@ParameterObject OrderPageQueryDTO orderPageQueryDTO) {
        log.info("查看待接订单：{}", orderPageQueryDTO);
        PageResult page = moverOrderService.getPendingOrders(orderPageQueryDTO);
        return Result.success(page);
    }

    @GetMapping("/list/ongoing")
    @Tag(name = "订单管理")
    @Operation(summary = "查看进行中的订单")
    public Result<PageResult> getOngoingOrders(@ParameterObject OngoingPageQueryDTO ongoingPageQueryDTO) {
        log.info("查看进行中的订单：{}", ongoingPageQueryDTO);
        PageResult page = moverOrderService.getOngoingOrders(ongoingPageQueryDTO);
        return Result.success(page);
    }

    @GetMapping("/list/completed")
    @Tag(name = "订单管理")
    @Operation(summary = "查看已完成的订单")
    public Result<PageResult> getOngoingOrders(@ParameterObject CompletedPageQueryDTO completedPageQueryDTO) {
        log.info("查看已完成的订单：{}", completedPageQueryDTO);
        PageResult page = moverOrderService.getCompletedOrders(completedPageQueryDTO);
        return Result.success(page);
    }

    @PostMapping("/dispatch/{id}/accept")
    @Tag(name = "订单管理")
    @Operation(summary = "接单")
    public Result accept(@PathVariable Long id){
        log.info("接单：{}",id);
        moverOrderService.accept(id);
        return Result.success();
    }

    @PostMapping("/dispatch/{id}/reject")
    @Tag(name = "订单管理")
    @Operation(summary = "拒单")
    public Result reject(@PathVariable Long id){
        log.info("拒单：{}",id);
        moverOrderService.reject(id);
        return Result.success();
    }

}
