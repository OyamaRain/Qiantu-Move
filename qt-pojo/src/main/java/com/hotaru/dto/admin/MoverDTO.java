package com.hotaru.dto.admin;

import lombok.Data;

import java.io.Serializable;

@Data
public class MoverDTO implements Serializable {

    // moverId
    private Long id;

    // 姓名
    private String name;

    // 手机号
    private String phone;

    // 头像
    private String avatar;

    // 评分
    private Double rating;

    // 完成订单数
    private Integer orderCount;

}
