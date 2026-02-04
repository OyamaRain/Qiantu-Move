package com.hotaru.service.admin.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotaru.constant.DispatchStatusConstant;
import com.hotaru.constant.MessageConstant;
import com.hotaru.constant.OrderStatusConstant;
import com.hotaru.dto.admin.OrderPageQueryDTO;
import com.hotaru.entity.Mover;
import com.hotaru.entity.MoverOrderDispatch;
import com.hotaru.entity.Order;
import com.hotaru.exception.DispatchOrderException;
import com.hotaru.exception.ExpectedMoverNotFoundException;
import com.hotaru.exception.OrderNotFoundException;
import com.hotaru.exception.OrderStatusErrorException;
import com.hotaru.mapper.DispatchMapper;
import com.hotaru.mapper.MoverMapper;
import com.hotaru.mapper.OrderMapper;
import com.hotaru.result.PageResult;
import com.hotaru.service.admin.AdminOrderService;
import com.hotaru.vo.admin.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminAdminOrderServiceImpl implements AdminOrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private MoverMapper moverMapper;
    @Autowired
    private DispatchMapper dispatchMapper;

    @Override
    public OrderVO list(Long id) {
        // 所有的拼接工作和关联查询都在 SQL 层面处理好了
        // 映射结果直接就是带地址名称的 OrderVO
        OrderVO orderVO = orderMapper.getDetailsById(id);

        if (orderVO == null) {
            throw new OrderNotFoundException("订单不存在");
        }

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
    public void dispatch(Long orderId, Long moverId) {

        // 校验订单
        Order order = orderMapper.getById(orderId);
        if (order == null) {
            throw new OrderNotFoundException(MessageConstant.ORDER_NOT_FOUND);
        }

        // 只有“已支付”的订单才能后台手动派单
        if (order.getStatus() != OrderStatusConstant.PAID
                && order.getStatus() != OrderStatusConstant.PENDING_ASSIGN) {
            throw new OrderStatusErrorException(MessageConstant.ORDER_STATUS_ERROR);
        }

        // 只要还有 WAITING，就不允许再次派单
        int waitingCount = dispatchMapper.countWaitingByOrderId(orderId);
        if (waitingCount > 0) {
            throw new DispatchOrderException("该订单已有待响应的派单");
        }

        // 插入派单记录
        MoverOrderDispatch dispatch = new MoverOrderDispatch();
        dispatch.setOrderId(orderId);
        dispatch.setMoverId(moverId);
        dispatch.setDispatchStatus(DispatchStatusConstant.WAITING);
        dispatch.setDispatchTime(LocalDateTime.now());
        dispatch.setCreateTime(LocalDateTime.now());
        dispatch.setUpdateTime(LocalDateTime.now());

        dispatchMapper.insert(dispatch);

        // 更新订单状态为待派单
        order.setStatus(OrderStatusConstant.PENDING_ASSIGN);
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
        if (order.getStatus() == OrderStatusConstant.COMPLETED) {
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

        // 更新师傅状态为空闲
        moverMapper.updateStatus(order.getMoverId(), 1);
    }

    @Override
    public List<Mover> recommendMover(Long orderId) {
        // 调用Mapper查询Movers
        List<Mover> movers = moverMapper.findMovers();

        // 检查是否找到合适的搬家师傅
        if(movers.isEmpty()){
            throw new ExpectedMoverNotFoundException(MessageConstant.MOVER_NOT_FOUND);
        }

        return movers;

    }


}
