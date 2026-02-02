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
public class UserInfoVO implements Serializable {

    //用户名
    private String nickname;

    //头像
    private String avatar;

    //手机号
    private String phone;

    //角色
    private String role;
}
