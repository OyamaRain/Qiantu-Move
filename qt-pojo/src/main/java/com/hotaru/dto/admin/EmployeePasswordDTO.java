package com.hotaru.dto.admin;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeePasswordDTO implements Serializable {
    // 员工ID
    private Long empId;

    // 新密码
    private String newPassword;

    // 旧密码
    private String oldPassword;
}
