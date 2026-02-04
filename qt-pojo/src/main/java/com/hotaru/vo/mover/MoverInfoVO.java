package com.hotaru.vo.mover;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MoverInfoVO implements Serializable {

    // 姓名
    private String name;

    // 头像
    private String avatar;

    // 手机号
    private String phone;

    // 角色
    private String role;
}
