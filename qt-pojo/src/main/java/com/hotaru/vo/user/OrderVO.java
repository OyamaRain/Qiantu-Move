package com.hotaru.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class OrderVO implements Serializable {
    //订单ID
    private Long id;

    //订单号
    private String orderNo;

    //搬家方式 --- 同城/跨城
    private String moveType;

    //起始地址
    private String startAddressName;

    //目的地
    private String endAddressName;

    //预约搬家时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime appointmentTime;

    //订单总金额
    private Double totalAmount;

    //订单创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}
