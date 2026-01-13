package com.hotaru.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ServiceItemDTO implements Serializable {

    // 服务项ID
    private Long id;

    // 服务项名称
    private String name;

    // 服务项目类型，0-基础服务，1-附加服务
    private Integer type;

    // 单位
    private String unit;

    // 价格
    private double price;

    // 创建时间
    private String createTime;

}
