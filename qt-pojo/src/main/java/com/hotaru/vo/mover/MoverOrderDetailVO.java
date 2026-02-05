package com.hotaru.vo.mover;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class MoverOrderDetailVO implements Serializable {
    // 订单号
    private String orderNo;

    // 服务项
    private String serviceItemName;

    // 时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime appointmentTime;

    // 搬家师傅
    private String moverName;

    // 搬家师傅电话
    private String moverPhone;

    // 搬家起始地址ID
    private String startAddressId;

    // 搬家起始地址
    private String startAddressName;

    // 搬家目的地ID
    private String endAddressId;

    // 搬家目的地
    private String endAddressName;

    // 搬家方式 --- 1：同城 2：跨城
    private String moveType;

    // 订单状态 0：未支付 1：已支付 2：待接单 3：已接单 4：已完成 5：已取消
    private String status;

    // 订单总金额
    private Double totalAmount;

    // 订单创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    // 订单最后更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    // 是否已评价
    private Integer isCommented;
}
