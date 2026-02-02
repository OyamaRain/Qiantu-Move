package com.hotaru.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserUpdateInfoDTO implements Serializable {

    // 昵称
    private String nickname;

    // 头像
    private String avatar;
}
