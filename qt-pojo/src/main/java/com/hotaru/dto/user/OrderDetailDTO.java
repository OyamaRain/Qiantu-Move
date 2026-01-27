package com.hotaru.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDetailDTO implements Serializable {

    //服务项ID
    private Long serviceItemId;

    //单价
    private Double price;

    //数量
    private Integer quantity;

}
