package com.hotaru.dto.admin;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeeLoginDTO implements Serializable {

    // 用户名
    private String username;

    // 密码
    private String password;
}
