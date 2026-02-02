package com.hotaru.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class ServiceItemPageQueryDTO implements Serializable {
    //服务项
    private String serviceItemName;

    //页码
    private Integer page;

    //每页显示记录数
    private Integer pageSize;
}
