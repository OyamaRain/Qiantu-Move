package com.hotaru.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

@Data
public class ServiceItemDTO implements Serializable {

    // 服务项ID
    private Long id;

    // 服务项名称
    private String name;

    //服务项目类型
    private Long categoryId;

    // 单位
    private String unit;

    // 价格
    private double price;

}
