package com.hotaru.mapper;

import com.github.pagehelper.Page;
import com.hotaru.dto.mover.OrderPageQueryDTO;
import com.hotaru.entity.MoverOrderDispatch;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DispatchMapper {
    void insert(MoverOrderDispatch dispatch);

    @Select("select * from mover_order_dispatch where order_id = #{orderId} and dispatch_status = 0")
    MoverOrderDispatch getByOrderId(Long orderId);

    void update(MoverOrderDispatch dispatch);

    int countWaitingByOrderId(Long orderId);

}
