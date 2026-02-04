package com.hotaru.service.mover.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotaru.constant.DispatchStatusConstant;
import com.hotaru.constant.MessageConstant;
import com.hotaru.constant.OrderStatusConstant;
import com.hotaru.context.BaseContext;
import com.hotaru.dto.mover.CompletedPageQueryDTO;
import com.hotaru.dto.mover.OngoingPageQueryDTO;
import com.hotaru.dto.mover.OrderPageQueryDTO;
import com.hotaru.entity.MoverOrderDispatch;
import com.hotaru.entity.Order;
import com.hotaru.exception.DispatchOrderException;
import com.hotaru.exception.OrderNotFoundException;
import com.hotaru.exception.OrderStatusErrorException;
import com.hotaru.mapper.DispatchMapper;
import com.hotaru.mapper.MoverMapper;
import com.hotaru.mapper.OrderMapper;
import com.hotaru.result.PageResult;
import com.hotaru.service.mover.MoverOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class MoverOrderServiceImpl implements MoverOrderService {

    @Autowired
    private DispatchMapper dispatchMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private MoverMapper moverMapper;

    @Override
    public PageResult getPendingOrders(OrderPageQueryDTO orderPageQueryDTO) {
        PageHelper.startPage(orderPageQueryDTO.getPage(), orderPageQueryDTO.getPageSize());

        Long currentId = BaseContext.getCurrentId();
        Page<Order> page = orderMapper.moverOrderPageQuery(orderPageQueryDTO,currentId);

        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    @Transactional
    public void accept(Long id) {
        // 查派单记录
        MoverOrderDispatch dispatch = dispatchMapper.getByOrderId(id);
        if (dispatch == null || dispatch.getDispatchStatus() != DispatchStatusConstant.WAITING) {
            throw new DispatchOrderException("派单状态非法");
        }

        // 查订单
        Order order = orderMapper.getById(dispatch.getOrderId());
        if (order.getStatus() != OrderStatusConstant.PENDING_ASSIGN) {
            throw new DispatchOrderException("订单状态非法");
        }

        // 更新派单记录
        dispatch.setDispatchStatus(DispatchStatusConstant.ASSIGNED);
        dispatch.setResponseTime(LocalDateTime.now());
        dispatchMapper.update(dispatch);

        // 更新订单
        order.setStatus(OrderStatusConstant.ASSIGNED);
        order.setMoverId(dispatch.getMoverId());
        order.setUpdateTime(LocalDateTime.now());
        orderMapper.update(order);

        // 更新师傅状态
        moverMapper.updateStatus(dispatch.getMoverId(),2);
    }

    @Override
    public void reject(Long id) {
        MoverOrderDispatch dispatch = dispatchMapper.getByOrderId(id);
        if (dispatch == null || dispatch.getDispatchStatus() != DispatchStatusConstant.WAITING) {
            throw new DispatchOrderException("派单状态非法");
        }

        dispatch.setDispatchStatus(DispatchStatusConstant.REJECTED);
        dispatch.setResponseTime(LocalDateTime.now());
        dispatch.setUpdateTime(LocalDateTime.now());
        dispatchMapper.update(dispatch);
    }

    @Override
    public PageResult getOngoingOrders(OngoingPageQueryDTO ongoingPageQueryDTO) {
        PageHelper.startPage(ongoingPageQueryDTO.getPage(), ongoingPageQueryDTO.getPageSize());

        Long moverId = BaseContext.getCurrentId();
        Page<Order> page = orderMapper.ongoingPageQuery(ongoingPageQueryDTO,moverId);

        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public PageResult getCompletedOrders(CompletedPageQueryDTO completedPageQueryDTO) {
        PageHelper.startPage(completedPageQueryDTO.getPage(), completedPageQueryDTO.getPageSize());

        Long moverId = BaseContext.getCurrentId();
        Page<Order> page = orderMapper.completedPageQuery(completedPageQueryDTO,moverId);

        return new PageResult(page.getTotal(), page.getResult());
    }

}
