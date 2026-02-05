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
import com.hotaru.entity.UserAddress;
import com.hotaru.exception.DispatchOrderException;
import com.hotaru.exception.OrderNotFoundException;
import com.hotaru.mapper.*;
import com.hotaru.result.PageResult;
import com.hotaru.service.mover.MoverOrderService;
import com.hotaru.vo.mover.MoverOrderDetailVO;
import com.hotaru.vo.user.UserOrderDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MoverOrderServiceImpl implements MoverOrderService {

    @Autowired
    private DispatchMapper dispatchMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private MoverMapper moverMapper;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private OrderServiceLogicMapper orderServiceLogicMapper;

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

    @Override
    public MoverOrderDetailVO getDetailsById(Long orderId) {
        // 1. 获取当前登录用户，防止水平越权
        Long userId = BaseContext.getCurrentId();

        // 2. 一次性联表查询订单、师傅、地址原始信息
        MoverOrderDetailVO detailVO = orderMapper.getDetailWithRelations4Mover(orderId, userId);

        if (detailVO == null) {
            throw new OrderNotFoundException(MessageConstant.ORDER_NOT_FOUND);
        }

        // 3. 格式化地址信息
        UserAddress start = addressMapper.getNameById(detailVO.getStartAddressId());
        UserAddress end = addressMapper.getNameById(detailVO.getEndAddressId());
        detailVO.setStartAddressName(formatAddress(start));
        detailVO.setEndAddressName(formatAddress(end));

        //4.组装服务项
        List<String> serviceNames = orderServiceLogicMapper.getServiceNamesByOrderNo(orderId);
        if (serviceNames != null && !serviceNames.isEmpty()) {
            detailVO.setServiceItemName(String.join(",", serviceNames));
        } else {
            detailVO.setServiceItemName("其他");
        }

        // 5. 设置状态
        detailVO.setStatus(convertStatusToText(detailVO.getStatus()));

        // 6. 设置搬家方式
        detailVO.setMoveType((detailVO.getMoveType().equals("1")) ? "同城" : "跨城");

        return detailVO;
    }

    // 格式化地址 --- 配合VO组装
    private String formatAddress(UserAddress addr) {
        if (addr == null) return "未知地址";
        if (addr.getProvince().equals(addr.getCity())) {
            return addr.getProvince() + addr.getDistrict() + addr.getDetailAddress();
        }else{
            return addr.getProvince() + addr.getCity() + addr.getDistrict() + addr.getDetailAddress();
        }
    }

    //状态码转换工具方法
    private String convertStatusToText(String statusCode) {
        if (statusCode == null) return "未知状态";

        switch (statusCode) {
            case "0": return "未支付";
            case "1": return "已支付";
            case "2": return "待接单";
            case "3": return "已接单";
            case "4": return "已完成";
            case "5": return "已取消";
            default: return "处理中";
        }
    }
}
