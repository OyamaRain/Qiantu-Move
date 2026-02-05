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
public class OngoingOrdersVO implements Serializable {
    // 进行中订单
    private Integer ongoing;
}
