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
public class MoverApply implements Serializable {

    //主键ID
    private Long id;

    //用户ID
    private Long userId;

    //姓名
    private String name;

    //手机号
    private String phone;

    //申请状态 --- 0：未处理 1：已通过 2：已拒绝
    private Integer applyStatus;

    //拒绝原因
    private String rejectReason;

    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}
