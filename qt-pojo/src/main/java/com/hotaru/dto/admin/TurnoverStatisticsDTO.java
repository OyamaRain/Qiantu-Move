package com.hotaru.dto.admin;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TurnoverStatisticsDTO implements Serializable {

    // 统计开始日期
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;

    // 统计结束日期
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

}
