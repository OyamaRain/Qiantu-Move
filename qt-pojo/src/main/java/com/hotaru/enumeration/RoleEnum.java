package com.hotaru.enumeration;

import lombok.Getter;

// 角色身份枚举类
@Getter
public enum RoleEnum {
    ADMIN("管理员"),
    STAFF("普通员工");

    private final String desc;

    RoleEnum(String desc) {
        this.desc = desc;
    }

}
