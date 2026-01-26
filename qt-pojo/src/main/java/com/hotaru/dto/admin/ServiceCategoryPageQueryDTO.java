package com.hotaru.dto.admin;

import lombok.Data;

import java.io.Serializable;

@Data
public class ServiceCategoryPageQueryDTO implements Serializable {

    // 类型名称
    private String name;

    // 状态，0-禁用，1-启用
    private Integer status;

    // 页码
    private int page;

    // 每页显示记录数
    private int pageSize;
}
