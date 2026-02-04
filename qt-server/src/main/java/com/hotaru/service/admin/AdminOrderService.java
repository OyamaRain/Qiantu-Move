package com.hotaru.service.admin;

import com.hotaru.dto.admin.OrderPageQueryDTO;
import com.hotaru.entity.Mover;
import com.hotaru.result.PageResult;
import com.hotaru.vo.admin.OrderVO;

import java.util.List;

public interface AdminOrderService {
    //查询订单
    OrderVO list(Long id);

    //分页查询订单
    PageResult pageQuery(OrderPageQueryDTO orderPageQueryDTO);

    //派单
    void dispatch(Long id,Long moverId);

    //取消
    void cancel(Long id);

    //完成
    void complete(Long id);

    //根据订单id查询推荐的搬家师傅
    List<Mover> recommendMover(Long orderId);
}
