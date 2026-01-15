package com.hotaru.dto;

import lombok.Data;

@Data
public class ServiceCategoryPageQueryDTO {

    // 类型名称
    private String name;

    // 状态，0-禁用，1-启用
    private Integer status;

    // 页码
    private int page;

    // 每页显示记录数
    private int pageSize;
}
