package com.hotaru.dto.mover;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommentPageQueryDTO implements Serializable {

    //师傅ID
    private Long moverId;

    //页码
    private Integer page;

    //每页显示记录数
    private Integer pageSize;
}
