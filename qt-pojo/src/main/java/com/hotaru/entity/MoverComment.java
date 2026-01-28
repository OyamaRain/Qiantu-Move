package com.hotaru.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MoverComment implements Serializable {
    // 主键ID
    private Long id;

    // 订单ID
    private Long orderId;

    // 师傅ID
    private Long moverId;

    // 用户ID
    private Long userId;

    // 服务评分
    private Integer score;

    // 服务评价内容
    private String content;

    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
