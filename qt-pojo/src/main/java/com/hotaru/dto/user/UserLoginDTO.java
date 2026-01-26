package com.hotaru.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginDTO implements Serializable {

    // 手机号
    private String phone;

    // 密码
    private String password;
}
