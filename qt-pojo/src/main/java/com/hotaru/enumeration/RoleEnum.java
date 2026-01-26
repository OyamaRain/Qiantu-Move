package com.hotaru.enumeration;

import lombok.Getter;

/**
 * 系统角色枚举
 * 用于权限控制、JWT 鉴权、多端区分
 */
@Getter
public enum RoleEnum {

    // 管理端
    ADMIN("ADMIN", "管理员"),
    STAFF("STAFF", "后台员工"),

    // 客户端
    USER("USER", "普通用户"),
    MOVER("MOVER", "搬家师傅");

    /**
     * 角色编码（用于存库 / JWT / Spring Security）
     */
    private final String code;

    /**
     * 角色描述（用于展示）
     */
    private final String desc;

    RoleEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
