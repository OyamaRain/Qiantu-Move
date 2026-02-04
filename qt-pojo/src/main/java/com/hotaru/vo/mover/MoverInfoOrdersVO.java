package com.hotaru.vo.mover;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MoverInfoOrdersVO implements Serializable {
    // 待服务数量
    private Integer toBeServed;

    // 已完成数量
    private Integer completed;
}
