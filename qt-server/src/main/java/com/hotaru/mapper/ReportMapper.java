package com.hotaru.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface ReportMapper {

    Integer getUsersSumBefore(LocalDateTime begin);

    List<Map<String, Object>> getNewUsersCountList(LocalDateTime begin, LocalDateTime end);

    List<Map<String, Object>> getTurnoverStatistics(LocalDateTime begin, LocalDateTime end);

    List<Map<String, Object>> getOrderStatisticsData(LocalDateTime begin, LocalDateTime end);
}
