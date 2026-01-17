package com.hotaru.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatisticsVO implements Serializable {

    // 日期
    private String dateList;

    // 订单总数
    private Integer totalOrderCount;

    // 有效订单数
    private Integer validOrderCount;

    // 订单完成率
    private Double orderCompletionRate;

    // 订单数列表
    private String orderCountList;

    // 有效订单列表
    private String validOrderCountList;
}
