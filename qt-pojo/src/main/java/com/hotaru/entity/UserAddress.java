package com.hotaru.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class UserAddress implements Serializable {
    // 主键
    private Long id;

    // 外键 --- 用户ID
    private Long userId;

    // 联系人
    private String contactName;

    // 联系电话
    private String contactPhone;

    // 省份
    private String province;

    // 城市
    private String city;

    // 区县
    private String district;

    // 详细地址
    private String detailAddress;

    // 楼层
    private Integer floor;

    // 是否有电梯 --- 0：无电梯，1：有电梯
    private Integer hasElevator;

    // 是否默认地址 --- 0：否，1：是
    private Integer isDefault;

    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    // 更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;


}
