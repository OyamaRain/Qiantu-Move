package com.hotaru.service.impl;

import com.hotaru.dto.OrderStatisticsDTO;
import com.hotaru.dto.TurnoverStatisticsDTO;
import com.hotaru.dto.UserStatisticsDTO;
import com.hotaru.mapper.ReportMapper;
import com.hotaru.service.ReportService;
import com.hotaru.vo.OrderStatisticsVO;
import com.hotaru.vo.TurnoverStatisticsVO;
import com.hotaru.vo.UserStatisticsVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportMapper reportMapper;

    @Override
    public UserStatisticsVO getUserStatistics(UserStatisticsDTO userStatisticsDTO) {
        LocalDateTime begin = userStatisticsDTO.getBeginTime();
        LocalDateTime end = userStatisticsDTO.getEndTime();

        // 1. 封装 DateList
        List<LocalDateTime> dateList = new ArrayList<>();
        LocalDateTime temp = begin;
        while (!temp.isAfter(end)) {
            dateList.add(temp);
            temp = temp.plusDays(1);
        }

        // 第一次调用：获取 beginTime 之前的总人数作为初始值
        Integer totalAccumulated = reportMapper.getUsersSumBefore(begin);
        if (totalAccumulated == null) totalAccumulated = 0;

        // 第二次调用：一次性查出这段时间内每天新增的人数
        // 返回值 Map 的 Key 是日期字符串 (2023-10-01), Value 是当天人数
        // 1. 调用 Mapper 获取 List
        List<Map<String, Object>> newList = reportMapper.getNewUsersCountList(begin, end.plusDays(1));

        // 2. 将 List 手动转为 Map<String, Integer>
        // 这样就把 {"date":"2026-01-17", "count":5} 变成了 "2026-01-17" -> 5
        Map<String, Integer> dailyNewUserMap = newList.stream().collect(Collectors.toMap(
                m -> m.get("date").toString(),
                m -> ((Number) m.get("count")).intValue()
        ));

        List<Integer> newUserList = new ArrayList<>();
        List<Integer> totalUserList = new ArrayList<>();

        // 遍历 DateList 集合
        for (LocalDateTime date : dateList) {
            // 从 Map 中获取当天新增人数，没查到说明当天是 0
            String dateKey = date.toLocalDate().toString();
            Integer dailyNewUser = dailyNewUserMap.getOrDefault(dateKey, 0);

            // 累计总人数 = 之前的总人数 + 当天新增
            totalAccumulated += dailyNewUser;

            newUserList.add(dailyNewUser);
            totalUserList.add(totalAccumulated);
        }

        // 封装 UserStatisticsVO
        return UserStatisticsVO.builder()
                .dateList(StringUtils.join(dateList.stream().map(d -> d.toLocalDate().toString()).collect(Collectors.toList()), ","))
                .totalUserList(StringUtils.join(totalUserList, ","))
                .newUserList(StringUtils.join(newUserList, ","))
                .build();
    }

    @Override
    public TurnoverStatisticsVO getTurnoverStatistics(TurnoverStatisticsDTO turnoverStatisticsDTO) {
        LocalDateTime begin = turnoverStatisticsDTO.getBeginTime();
        LocalDateTime end = turnoverStatisticsDTO.getEndTime();

        // 封装 DateList
        List<LocalDateTime> dateList = new ArrayList<>();
        LocalDateTime temp = begin;
        while (!temp.isAfter(end)) {
            dateList.add(temp);
            temp = temp.plusDays(1);
        }

        // 获取当天的总流水
        List<Map<String,Object>> totalList = reportMapper.getTurnoverStatistics(begin, end);

        Map<String, Integer> dailyTurnoverMap = totalList.stream().collect(Collectors.toMap(
                m -> m.get("date").toString(),
                m -> ((Number) m.get("turnover")).intValue()
        ));

        List<Integer> turnoverList = new ArrayList<>();

        // 遍历 DateList 集合
        for (LocalDateTime date : dateList) {
            // 从 Map 中获取当天流水额，没查到说明当天是 0
            String dateKey = date.toLocalDate().toString();
            Integer dailyTurnover = dailyTurnoverMap.getOrDefault(dateKey, 0);
            turnoverList.add(dailyTurnover);
        }

        // 封装 TurnoverStatisticsVO
        return TurnoverStatisticsVO.builder()
                .dateList(StringUtils.join(dateList.stream().map(d -> d.toLocalDate().toString()).collect(Collectors.toList()), ","))
                .turnoverList(StringUtils.join(turnoverList, ","))
                .build();

    }

    @Override
    public OrderStatisticsVO getOrderStatistics(OrderStatisticsDTO orderStatisticsDTO) {
        LocalDateTime begin = orderStatisticsDTO.getBeginTime();
        LocalDateTime end = orderStatisticsDTO.getEndTime();

        // 1. 生成日期序列
        List<String> dateStrList = new ArrayList<>();
        LocalDateTime temp = begin;
        while (!temp.isAfter(end)) {
            dateStrList.add(temp.toLocalDate().toString());
            temp = temp.plusDays(1);
        }

        // 2. 核心：只调用一次数据库 (记得给 end 加一天以覆盖当天全时段)
        List<Map<String, Object>> rawData = reportMapper.getOrderStatisticsData(begin, end.plusDays(1));

        // 3. 将 List 转为 Map，方便按日期快速取值
        Map<String, Map<String, Object>> dataMap = rawData.stream()
                .collect(Collectors.toMap(m -> m.get("date").toString(), m -> m));

        // 4. 定义统计变量
        List<Integer> orderCountList = new ArrayList<>();      // 每日总订单
        List<Integer> validOrderCountList = new ArrayList<>(); // 每日有效订单
        int totalOrderCount = 0; // 累计总订单数
        int validOrderCount = 0; // 累计有效订单数

        // 5. 遍历日期序列，从 Map 中取值并累加
        for (String dateKey : dateStrList) {
            Map<String, Object> dayData = dataMap.get(dateKey);

            int dayOrder = 0;
            int dayValid = 0;

            if (dayData != null) {
                dayOrder = ((Number) dayData.get("dayOrderCount")).intValue();
                dayValid = ((Number) dayData.get("dayValidOrderCount")).intValue();
            }

            orderCountList.add(dayOrder);
            validOrderCountList.add(dayValid);

            totalOrderCount += dayOrder; // 在内存里累加，不再查数据库
            validOrderCount += dayValid;
        }

        // 6. 计算完成率
        Double completionRate = 0.0;
        if (totalOrderCount > 0) {
            // 逻辑：valid / total
            BigDecimal v = BigDecimal.valueOf(validOrderCount);
            BigDecimal t = BigDecimal.valueOf(totalOrderCount);

            // 保留 4 位小数（即百分比形式下的 2 位，如 0.1234 代表 12.34%）
            completionRate = v.divide(t, 4, RoundingMode.HALF_UP).doubleValue();
        }

        // 7. 封装返回
        return OrderStatisticsVO.builder()
                .dateList(StringUtils.join(dateStrList, ","))
                .orderCountList(StringUtils.join(orderCountList, ","))
                .validOrderCountList(StringUtils.join(validOrderCountList, ","))
                .totalOrderCount(totalOrderCount)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(completionRate)
                .build();
    }
}
