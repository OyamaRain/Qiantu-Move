package com.hotaru.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderCommentDTO implements Serializable {

    // 订单id
    private Long orderId;

    // 服务评分
    private Integer orderScore;

    // 服务评价内容
    private String orderContent;

    // 师傅评分
    private Integer moverScore;

    // 师傅评价内容
    private String moverContent;


}
