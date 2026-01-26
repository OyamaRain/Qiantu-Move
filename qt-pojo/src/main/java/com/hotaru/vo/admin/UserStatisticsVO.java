package com.hotaru.vo.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserStatisticsVO implements Serializable {

    // 日期列表
    private String dateList;

    // 新增用户数列表
    private String newUserList;

    // 总用户数列表
    private String totalUserList;

}
