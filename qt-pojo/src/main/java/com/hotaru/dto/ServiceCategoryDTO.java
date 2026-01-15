package com.hotaru.dto;

import lombok.Data;


@Data
public class ServiceCategoryDTO {

    // 分类ID
    private Long id;

    // 分类名称
    private String name;

    // 排序值
    private Integer sort;

}
