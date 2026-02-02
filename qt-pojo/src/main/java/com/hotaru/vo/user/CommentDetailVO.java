package com.hotaru.vo.user;

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
public class CommentDetailVO implements Serializable {

    // 订单号
    private String orderNo;

    // 订单评分
    private Integer orderScore;

    // 评价内容
    private String orderContent;

    // 师傅名称
    private String moverName;

    // 师傅头像
    private String moverAvatar;

    // 师傅评分
    private Integer moverScore;

    // 师傅评价内容
    private String moverContent;

    // 评价时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}
