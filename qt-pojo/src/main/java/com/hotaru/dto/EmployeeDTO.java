package com.hotaru.dto;

import com.hotaru.enumeration.RoleEnum;
import lombok.Data;
import java.io.Serializable;

@Data
public class EmployeeDTO implements Serializable {
    // employeeId
    private Long id;

    // 员工账号
    private String username;

    // 员工姓名
    private String name;

    // 身份
    private RoleEnum role;

    // 性别 --- 0-女，1-男
    private Integer sex;

    // 手机号
    private String phone;

    // 头像
    private String avatar;

}
