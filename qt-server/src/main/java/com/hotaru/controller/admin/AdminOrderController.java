package com.hotaru.controller.admin;

import com.hotaru.annotation.Log;
import com.hotaru.dto.admin.OrderPageQueryDTO;
import com.hotaru.entity.Mover;
import com.hotaru.result.PageResult;
import com.hotaru.result.Result;
import com.hotaru.service.admin.AdminOrderService;
import com.hotaru.vo.admin.OrderVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/orders")
@Slf4j
public class AdminOrderController {

    @Autowired
    private AdminOrderService orderService;

    @GetMapping("/details/{id}")
    @Tag(name = "订单管理")
    @Operation(summary = "根据ID查询订单详情")
    public Result<OrderVO> orderDetail(@PathVariable Long id) {
        log.info("查询订单{}详情", id);
        OrderVO orderVO = orderService.list(id);
        return Result.success(orderVO);
    }

    @GetMapping("/page")
    @Tag(name = "订单管理")
    @Operation(summary = "分页查询订单列表")
    public Result<PageResult> orderPage(@ParameterObject OrderPageQueryDTO orderPageQueryDTO) {
        log.info("分页查询订单列表：{}", orderPageQueryDTO);
        PageResult pageResult = orderService.pageQuery(orderPageQueryDTO);
        return Result.success(pageResult);
    }

    @Log("派送订单")
    @PostMapping("/{id}/dispatch")
    @Tag(name = "订单管理")
    @Operation(summary = "派送订单")
    public Result dispatch(@PathVariable Long id) {
        log.info("指派订单{}", id);
        orderService.dispatch(id);
        return Result.success();
    }

    @Log("取消订单")
    @PostMapping("/{id}/cancel")
    @Tag(name = "订单管理")
    @Operation(summary = "取消订单")
    public Result cancel(@PathVariable Long id) {
        log.info("取消订单：{}", id);
        orderService.cancel(id);
        return Result.success();
    }

    @Log("完成订单")
    @PostMapping("/{id}/complete")
    @Tag(name = "订单管理")
    @Operation(summary = "完成订单")
    public Result complete(@PathVariable Long id) {
        log.info("完成订单：{}", id);
        orderService.complete(id);
        return Result.success();
    }

    @GetMapping("/recommend")
    @Tag(name = "订单管理")
    @Operation(summary = "根据订单ID查询推荐的搬家师傅")
    public Result<List<Mover>> recommentMover(@RequestParam("orderId") Long orderId) {
        log.info("根据订单id查询推荐的搬家师傅：{}", orderId);
        List<Mover> movers = orderService.recommendMover(orderId);
        return Result.success(movers);
    }




}
