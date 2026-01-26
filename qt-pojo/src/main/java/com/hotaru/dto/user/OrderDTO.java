package com.hotaru.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class OrderDTO implements Serializable {

    //搬家方式 --- 1：同城 2：跨城
    private Integer moveType;

    //起始地址
    private String startAddressId;

    //目的地
    private String endAddressId;

    //预约搬家时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime appointmentTime;

    //订单总金额
    private Double totalAmount;

    //订单创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}
