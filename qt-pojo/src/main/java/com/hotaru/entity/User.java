package com.hotaru.entity;

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
public class User implements Serializable {

    // userId
    private Long id;

    // openid
    private String openId;

    // 电话号码
    private String phone;

    // 昵称
    private String nickname;

    // 角色
    private RoleEnum role;

    // 头像
    private String avatar;

    // 状态 --- 0 正常 1 禁用
    private Integer status;

    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    // 更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
