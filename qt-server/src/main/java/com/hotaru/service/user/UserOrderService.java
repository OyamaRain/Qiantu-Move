package com.hotaru.service.user;

import com.hotaru.dto.user.OrderDTO;
import com.hotaru.vo.user.OrderVO;

public interface UserOrderService {
    // 下单
    OrderVO createOrder(OrderDTO orderDTO);
}
