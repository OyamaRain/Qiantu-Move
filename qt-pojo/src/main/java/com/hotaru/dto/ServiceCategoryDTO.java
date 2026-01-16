package com.hotaru.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class ServiceCategoryDTO implements Serializable {

    // 分类ID
    private Long id;

    // 分类名称
    private String name;

    // 排序值
    private Integer sort;

}
