package com.hotaru.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVO {
    //订单id
    private Long id;

    //用户id
    private  Long userId;

    //搬家方式 --- 1：同城 2：跨城
    private Integer moveType;

    //起始地址
    private String startAddress;

    //目的地
    private String endAddress;

    //订单状态 0：未支付 1：已支付 2：待接单 3：已接单 4：已完成 5：已取消
    private Integer status;

    //订单创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String createTime;

    //订单更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String updateTime;

}
