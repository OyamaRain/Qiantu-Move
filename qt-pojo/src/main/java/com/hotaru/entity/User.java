package com.hotaru.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {

    // userId
    private Long id;

    // 电话号码
    private String phone;

    // 昵称
    private String nickname;

    // 头像URL
    private String avatar;

    // 状态 --- 0 正常 1 禁用
    private Integer status;

    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    // 更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
