package com.hotaru.mapper;

import com.hotaru.entity.OrderComment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderCommentMapper {
    void insert(OrderComment orderComment);
}
