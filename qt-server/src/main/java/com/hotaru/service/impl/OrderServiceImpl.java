package com.hotaru.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotaru.constant.MessageConstant;
import com.hotaru.constant.MoverStatusConstant;
import com.hotaru.constant.OrderStatusConstant;
import com.hotaru.dto.OrderPageQueryDTO;
import com.hotaru.entity.Mover;
import com.hotaru.entity.Order;
import com.hotaru.exception.ExpectedMoverNotFoundException;
import com.hotaru.exception.OrderNotFoundException;
import com.hotaru.exception.OrderStatusErrorException;
import com.hotaru.mapper.MoverMapper;
import com.hotaru.mapper.OrderMapper;
import com.hotaru.result.PageResult;
import com.hotaru.service.OrderService;
import com.hotaru.vo.OrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private MoverMapper moverMapper;

    @Override
    public OrderVO list(Long id) {
        Order order = orderMapper.getById(id);
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order,orderVO);
        return orderVO;
    }

    @Override
    public PageResult pageQuery(OrderPageQueryDTO orderPageQueryDTO) {
        // 开始分页查询
        PageHelper.startPage(orderPageQueryDTO.getPage(), orderPageQueryDTO.getPageSize());

        // 调用Mapper查询订单列表
        Page<Order> page = orderMapper.pageQuery(orderPageQueryDTO);

        // 封装PageResult
        long total = page.getTotal();
        List<Order> result = page.getResult();

        return new PageResult(total,result);
    }

    @Override
    @Transactional
    public void dispatch(Long id) {
        // 调用Mapper查询订单
        Order order = orderMapper.getById(id);

        // 检查订单是否存在
        if (order == null) {
            throw new OrderNotFoundException(MessageConstant.ORDER_NOT_FOUND);
        }

        // 检查订单状态是否为待接单
        if(order.getStatus() != OrderStatusConstant.PENDING_ASSIGN){
            throw new OrderStatusErrorException(MessageConstant.ORDER_STATUS_ERROR);
        }

        // 查找合适的搬家师傅
        Mover mover = Mover.builder()
                .status(MoverStatusConstant.IDLE)
                .build();
        List<Mover> expectedMover = moverMapper.findMover(mover);

        // 检查是否找到合适的搬家师傅
        if(expectedMover.isEmpty()){
            throw new ExpectedMoverNotFoundException(MessageConstant.MOVER_NOT_FOUND);
        }

        // 更新订单状态为已接单
        Mover selectedMover = expectedMover.get(0);
        order.setStatus(OrderStatusConstant.ASSIGNED);
        order.setMoverId(selectedMover.getId());
        order.setUpdateTime(LocalDateTime.now());
        orderMapper.update(order);
    }

    @Override
    @Transactional
    public void cancel(Long id) {
        // 调用Mapper查询订单
        Order order = orderMapper.getById(id);

        // 检查订单是否存在
        if (order == null) {
            throw new OrderNotFoundException(MessageConstant.ORDER_NOT_FOUND);
        }

        // 检查订单状态是否为已接单
        if (order.getStatus() == OrderStatusConstant.ASSIGNED
            || order.getStatus() == OrderStatusConstant.COMPLETED) {
            throw new OrderStatusErrorException(MessageConstant.ORDER_STATUS_ERROR);
        }

        // 更新订单状态为已取消
        //TODO 后续同步修改搬家师傅的状态
        order.setStatus(OrderStatusConstant.CANCELLED);
        order.setUpdateTime(LocalDateTime.now());
        orderMapper.update(order);
    }

    @Override
    @Transactional
    public void complete(Long id) {
        // 调用Mapper查询订单
        Order order = orderMapper.getById(id);

        // 检查订单是否存在
        if (order == null) {
            throw new OrderNotFoundException(MessageConstant.ORDER_NOT_FOUND);
        }

        // 检查订单状态是否为已接单
        if(order.getStatus() != OrderStatusConstant.ASSIGNED){
            throw new OrderStatusErrorException(MessageConstant.ORDER_STATUS_ERROR);
        }

        // 更新订单状态为已完成
        order.setStatus(OrderStatusConstant.COMPLETED);
        order.setUpdateTime(LocalDateTime.now());
        orderMapper.update(order);
    }


}
