package com.hotaru.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MoverPageQueryDTO implements Serializable {

    // 类型名称
    private String name;

    // 手机号
    private String phone;

    // 状态，0-禁用，1-空闲，2-忙碌
    private Integer status;

    // 页码
    private int page;

    // 每页显示记录数
    private int pageSize;
}
