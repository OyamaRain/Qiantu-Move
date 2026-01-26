package com.hotaru.vo.admin;

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
public class MoverVO implements Serializable {

    // moverId
    private Long id;

    // 姓名
    private String name;

    // 手机号
    private String phone;

    // 头像
    private String avatar;

    // 状态
    private Integer status;

    // 评分
    private Double rating;

    // 完成订单数
    private Integer orderCount;

    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    // 更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
