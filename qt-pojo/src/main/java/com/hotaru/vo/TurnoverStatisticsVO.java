package com.hotaru.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TurnoverStatisticsVO implements Serializable {

    // 日期列表
    private String dateList;

    // 流水列表
    private String turnoverList;

}
