package com.hotaru.service.user;

import com.hotaru.dto.user.OrderCommentDTO;
import com.hotaru.dto.user.OrderDTO;
import com.hotaru.dto.user.OrderPageQueryDTO;
import com.hotaru.result.PageResult;
import com.hotaru.vo.user.OrderVO;
import com.hotaru.vo.user.UserOrderDetailVO;
import com.hotaru.vo.user.UserOrderMoverVO;

public interface UserOrderService {
    // 下单
    OrderVO createOrder(OrderDTO orderDTO);

    // 支付订单
    void payOrder(Long orderId);

    // 分页查询
    PageResult pageQuery(OrderPageQueryDTO orderPageQueryDTO);

    // 根据ID查询订单详情
    UserOrderDetailVO getDetailsById(Long orderId);

    // 取消订单
    void cancelOrder(Long orderId);

    // 根据订单ID查询师傅信息
    UserOrderMoverVO getMoverInfo(Long orderId);

    // 评价订单
    void commentOrder(OrderCommentDTO orderCommentDTO);
}
