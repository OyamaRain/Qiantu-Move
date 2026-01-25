package com.hotaru.service;

import com.hotaru.dto.OrderPageQueryDTO;
import com.hotaru.entity.Mover;
import com.hotaru.result.PageResult;
import com.hotaru.vo.OrderVO;

import java.util.List;

public interface OrderService {
    //查询订单
    OrderVO list(Long id);

    //分页查询订单
    PageResult pageQuery(OrderPageQueryDTO orderPageQueryDTO);

    //派单
    void dispatch(Long id);

    //取消
    void cancel(Long id);

    //完成
    void complete(Long id);

    //根据订单id查询推荐的搬家师傅
    List<Mover> recommendMover(Long orderId);
}
