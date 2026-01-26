package com.hotaru.service.admin;

import com.hotaru.dto.admin.OrderStatisticsDTO;
import com.hotaru.dto.admin.TurnoverStatisticsDTO;
import com.hotaru.dto.admin.UserStatisticsDTO;
import com.hotaru.vo.admin.OrderStatisticsVO;
import com.hotaru.vo.admin.TurnoverStatisticsVO;
import com.hotaru.vo.admin.UserStatisticsVO;

public interface ReportService {
    // 获取用户统计数据
    UserStatisticsVO getUserStatistics(UserStatisticsDTO userStatisticsDTO);

    // 获取流水统计数据
    TurnoverStatisticsVO getTurnoverStatistics(TurnoverStatisticsDTO turnoverStatisticsDTO);

    // 获取订单统计数据
    OrderStatisticsVO getOrderStatistics(OrderStatisticsDTO orderStatisticsDTO);
}
