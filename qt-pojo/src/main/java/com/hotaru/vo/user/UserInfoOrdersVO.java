package com.hotaru.vo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoOrdersVO implements Serializable {
    // 待服务数量
    private Integer toBeServed;

    // 已完成数量
    private Integer completed;
}
