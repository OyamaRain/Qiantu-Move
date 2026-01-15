package com.hotaru.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ServiceItemPageQueryDTO implements Serializable {

    //服务名称
    private String name;

    //服务项目类型
    private Long categoryId;

    //服务状态
    private Integer status;

    //页码
    private int page;

    //每页显示记录数
    private int pageSize;

}
