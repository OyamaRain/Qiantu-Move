package com.hotaru.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hotaru.enumeration.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeVO implements Serializable {
    // employeeId
    private Long id;

    // 员工账号
    private String username;

    // 员工密码
    private String password;

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

    // 状态 --- 0-禁用，1-正常
    private Integer status;

    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    // 更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
