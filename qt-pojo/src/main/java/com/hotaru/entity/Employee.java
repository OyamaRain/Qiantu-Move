package com.hotaru.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
public class Employee implements Serializable {
    // employeeId
    private Long id;

    // 员工账号
    private String username;

    // 员工密码
    private String password;

    // 员工姓名
    private String name;

    // 角色 --- 1-管理员，2-调度员，3-客服
    private Integer role;

    // 状态 --- 0-禁用，1-正常
    private Integer status;

    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    // 更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
