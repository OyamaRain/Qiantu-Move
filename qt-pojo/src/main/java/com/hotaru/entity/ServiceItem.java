package com.hotaru.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
public class ServiceItem implements Serializable {
    //服务项目ID，主键
    private Long id;

    //服务项目名称
    private String name;

    //服务项目编码
    private Integer code;

    //服务项目类型，0-基础服务，1-附加服务
    private Integer type;

    //单位
    private String unit;

    //价格
    private double price;

    //状态，0-禁用，1-启用
    private Integer status;

    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
