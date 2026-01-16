package com.hotaru.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class OrderPageQueryDTO implements Serializable {

    // 订单号
    private String orderNo;

    // 搬家方式 --- 1：同城 2：跨城
    private Integer moveType;

    // 订单状态 0：未支付 1：已支付 2：待接单 3：已接单 4：已完成 5：已取消
    private Integer status;

    // 起始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;

    // 结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    // 页码
    private int page;

    // 每页显示数量
    private int pageSize;
}
