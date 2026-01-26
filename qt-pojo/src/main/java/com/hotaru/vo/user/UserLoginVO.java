package com.hotaru.vo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginVO implements Serializable {

    //主键值UserId
    private Long id;

    //用户名
    private String nickname;

    //手机号
    private String phone;

    //jwt令牌
    private String token;

}
