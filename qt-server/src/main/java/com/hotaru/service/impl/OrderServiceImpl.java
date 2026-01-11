package com.hotaru.service.impl;

import com.hotaru.mapper.OrderMapper;
import com.hotaru.service.OrderService;
import com.hotaru.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public OrderVO list(Long id) {
        OrderVO order = orderMapper.getById(id);
        return order;
    }
}
