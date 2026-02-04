package com.hotaru.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class MoverApplyDTO implements Serializable {

    //用户姓名
    private String Name;

    //用户手机号
    private String phone;

}
