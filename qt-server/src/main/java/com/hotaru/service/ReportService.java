package com.hotaru.service;

import com.hotaru.dto.OrderStatisticsDTO;
import com.hotaru.dto.TurnoverStatisticsDTO;
import com.hotaru.dto.UserStatisticsDTO;
import com.hotaru.vo.OrderStatisticsVO;
import com.hotaru.vo.TurnoverStatisticsVO;
import com.hotaru.vo.UserStatisticsVO;

public interface ReportService {
    // 获取用户统计数据
    UserStatisticsVO getUserStatistics(UserStatisticsDTO userStatisticsDTO);

    // 获取流水统计数据
    TurnoverStatisticsVO getTurnoverStatistics(TurnoverStatisticsDTO turnoverStatisticsDTO);

    // 获取订单统计数据
    OrderStatisticsVO getOrderStatistics(OrderStatisticsDTO orderStatisticsDTO);
}
