package com.hotaru.vo.mover;

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
public class MoverCommentVO implements Serializable {

    private Long id;

    // 1. 用户信息脱敏 (展示为：张** 或 匿名用户)
    private String userName;

    // 2. 评价核心分值
    private Integer score; // 星级：1-5

    // 3. 评价内容 (后端已处理：3分以下替换为“服务有待改进”)
    private String content;

    // 5. 时间维度 (建议模糊化处理，只显示年月，或“3天前”，增加用户匿名保护)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

}
