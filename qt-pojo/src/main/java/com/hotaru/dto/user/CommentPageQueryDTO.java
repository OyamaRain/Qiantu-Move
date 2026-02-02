package com.hotaru.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommentPageQueryDTO implements Serializable {
    //用户ID
    private Long userId;

    //页码
    private Integer page;

    //每页显示记录数
    private Integer pageSize;
}
