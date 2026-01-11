package com.hotaru.service;

import com.hotaru.vo.OrderVO;

public interface OrderService {
    //查询订单
    OrderVO list(Long id);
}
