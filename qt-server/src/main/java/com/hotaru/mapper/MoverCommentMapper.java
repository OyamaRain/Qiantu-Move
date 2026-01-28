package com.hotaru.mapper;

import com.hotaru.entity.MoverComment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MoverCommentMapper {
    void insert(MoverComment moverComment);
}
