package com.hotaru.service.mover;

import com.hotaru.dto.mover.CompletedPageQueryDTO;
import com.hotaru.dto.mover.OngoingPageQueryDTO;
import com.hotaru.dto.mover.OrderPageQueryDTO;
import com.hotaru.result.PageResult;
import com.hotaru.vo.mover.MoverOrderDetailVO;
import com.hotaru.vo.user.UserOrderDetailVO;

public interface MoverOrderService {
    //查看待接订单
    PageResult getPendingOrders(OrderPageQueryDTO orderPageQueryDTO);

    //接单
    void accept(Long id);

    //拒单
    void reject(Long id);

    //查看进行中的订单
    PageResult getOngoingOrders(OngoingPageQueryDTO ongoingPageQueryDTO);

    //查看已完成的订单
    PageResult getCompletedOrders(CompletedPageQueryDTO completedPageQueryDTO);

    //查看订单详情
    MoverOrderDetailVO getDetailsById(Long orderId);

}
