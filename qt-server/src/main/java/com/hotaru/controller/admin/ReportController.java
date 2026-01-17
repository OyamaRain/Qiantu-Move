package com.hotaru.controller.admin;

import com.hotaru.dto.OrderStatisticsDTO;
import com.hotaru.dto.TurnoverStatisticsDTO;
import com.hotaru.dto.UserStatisticsDTO;
import com.hotaru.result.Result;
import com.hotaru.service.ReportService;
import com.hotaru.vo.OrderStatisticsVO;
import com.hotaru.vo.TurnoverStatisticsVO;
import com.hotaru.vo.UserStatisticsVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/report")
@Slf4j
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/userStatistics")
    @Tag(name = "数据统计")
    @Operation(summary = "用户统计")
    public Result<UserStatisticsVO> userStatistics(@ParameterObject UserStatisticsDTO userStatisticsDTO) {
        log.info("用户统计：{}", userStatisticsDTO);
        UserStatisticsVO userStatisticsVO = reportService.getUserStatistics(userStatisticsDTO);
        return Result.success(userStatisticsVO);
    }

    @GetMapping("/turnoverStatistics")
    @Tag(name = "数据统计")
    @Operation(summary = "流水统计")
    public Result <TurnoverStatisticsVO> turnoverStatistics(@ParameterObject TurnoverStatisticsDTO turnoverStatisticsDTO) {
        log.info("流水统计：{}", turnoverStatisticsDTO);
        TurnoverStatisticsVO turnoverStatisticsVO = reportService.getTurnoverStatistics(turnoverStatisticsDTO);
        return Result.success(turnoverStatisticsVO);
    }

    @GetMapping("/ordersStatistics")
    @Tag(name = "数据统计")
    @Operation(summary = "订单统计")
    public Result<OrderStatisticsVO> ordersStatistics(@ParameterObject OrderStatisticsDTO orderStatisticsDTO) {
        log.info("订单统计：{}", orderStatisticsDTO);
        OrderStatisticsVO orderStatisticsVO = reportService.getOrderStatistics(orderStatisticsDTO);
        return Result.success(orderStatisticsVO);
    }
}
