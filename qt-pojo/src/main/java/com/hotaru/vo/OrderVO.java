package com.hotaru.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVO implements Serializable {
    //id
    private Long id;

    //订单号
    private String orderNo;

    //用户id
    private  Long userId;

    //师傅id
    private Long moverId;

    //搬家方式 --- 1：同城 2：跨城
    private Integer moveType;

    //起始地址
    private String startAddressId;

    //目的地
    private String endAddressId;

    //预约搬家时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date appointmentTime;

    //订单总金额
    private Double totalAmount;

    //订单状态 0：未支付 1：已支付 2：待接单 3：已接单 4：已完成 5：已取消
    private Integer status;

    //订单创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String createTime;

    //订单更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String updateTime;

}
