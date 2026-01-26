package com.hotaru.dto.admin;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class LogPageQueryDTO implements Serializable {

    // 操作人姓名
    private String employeeName;

    // 操作开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;

    // 操作结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    // 页码
    private int page;

    // 每页显示记录数
    private int pageSize;

}
