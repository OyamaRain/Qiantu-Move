package com.hotaru.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysLog {
    // 日志ID
    private Long id;

    // 操作人ID
    private Long employeeId;

    // 操作人姓名
    private String employeeName;

    // 操作描述
    private String operation;

    // 方法名
    private String method;

    // 创建时间
    private LocalDateTime createTime;
}
