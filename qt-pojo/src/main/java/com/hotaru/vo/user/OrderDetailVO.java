package com.hotaru.vo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailVO implements Serializable {
    // 服务项名称
    private String serviceItemName;

    // 数量
    private Integer quantity;

    // 单价
    private Double price;
}
