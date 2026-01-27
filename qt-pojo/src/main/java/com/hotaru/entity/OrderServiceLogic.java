package com.hotaru.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderServiceLogic implements Serializable {
    //主键ID
    private Long id;

    //订单ID
    private Long orderId;

    //服务项ID
    private Long serviceItemId;

    //数量
    private Integer quantity;

    //单价
    private Double unitPrice;

    //小计金额
    private Double subtotalPrice;

    //创建时间
    private LocalDateTime createTime;
}
