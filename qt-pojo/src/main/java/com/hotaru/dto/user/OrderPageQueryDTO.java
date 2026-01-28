package com.hotaru.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderPageQueryDTO implements Serializable {

    //用户ID
    private Long userId;

    //页码
    private Integer page;

    //每页显示记录数
    private Integer pageSize;

    //选项
    private Integer tabType;

    //订单号
    private String orderNo;
}
