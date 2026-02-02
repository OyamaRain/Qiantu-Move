package com.hotaru.mapper;

import com.hotaru.entity.OrderComment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderCommentMapper {
    void insert(OrderComment orderComment);

    @Select("select * from order_comment where order_id = #{orderId}")
    OrderComment getByOrderId(Long id);
}
