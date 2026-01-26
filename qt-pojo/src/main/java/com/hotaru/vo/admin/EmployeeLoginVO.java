package com.hotaru.vo.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeLoginVO implements Serializable {

    //主键值
    private Long id;

    //用户名
    private String userName;

    //姓名
    private String name;

    //jwt令牌
    private String token;

}
