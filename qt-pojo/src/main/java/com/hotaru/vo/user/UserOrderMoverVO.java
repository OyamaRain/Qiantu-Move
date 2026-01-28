package com.hotaru.vo.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderMoverVO implements Serializable {
    // 师傅id
    private Long moverId;

    // 师傅姓名
    private String moverName;
}
