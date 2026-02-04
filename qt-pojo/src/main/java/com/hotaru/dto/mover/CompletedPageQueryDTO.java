package com.hotaru.dto.mover;

import lombok.Data;

import java.io.Serializable;

@Data
public class CompletedPageQueryDTO implements Serializable {

    //页码
    private Integer page;

    //每页显示数量
    private Integer pageSize;

}
