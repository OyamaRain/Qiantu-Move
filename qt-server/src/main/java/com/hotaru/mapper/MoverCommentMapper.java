package com.hotaru.mapper;

import com.hotaru.entity.MoverComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MoverCommentMapper {
    void insert(MoverComment moverComment);

    @Select("select * from mover_comment where order_id = #{orderId}")
    MoverComment getByOrderId(Long id);
}
