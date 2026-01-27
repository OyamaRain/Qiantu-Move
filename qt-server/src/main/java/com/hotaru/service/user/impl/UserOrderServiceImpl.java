package com.hotaru.service.user.impl;

import com.hotaru.constant.OrderStatusConstant;
import com.hotaru.constant.ServiceItemConstant;
import com.hotaru.dto.user.OrderDTO;
import com.hotaru.dto.user.OrderDetailDTO;
import com.hotaru.entity.Order;
import com.hotaru.entity.OrderServiceLogic;
import com.hotaru.entity.ServiceItem;
import com.hotaru.entity.UserAddress;
import com.hotaru.exception.ServiceItemNotFoundException;
import com.hotaru.mapper.AddressMapper;
import com.hotaru.mapper.OrderMapper;
import com.hotaru.mapper.OrderServiceLogicMapper;
import com.hotaru.mapper.ServiceItemMapper;
import com.hotaru.service.user.UserOrderService;
import com.hotaru.utils.OrderUtils;
import com.hotaru.vo.user.OrderDetailVO;
import com.hotaru.vo.user.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserOrderServiceImpl implements UserOrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderServiceLogicMapper orderServiceLogicMapper;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private ServiceItemMapper serviceItemMapper;

    @Override
    @Transactional
    public OrderVO createOrder(OrderDTO orderDTO) {
        //生成订单号
        String orderNo = OrderUtils.generateOrderSn();

        LocalDateTime now = LocalDateTime.now();
        List<OrderDetailDTO> itemDTOs = orderDTO.getOrderItems();

        // 提取DTO中的ID
        List<Long> serviceItemIds = itemDTOs.stream()
                .map(OrderDetailDTO::getServiceItemId)
                .distinct()
                .collect(Collectors.toList());

        // 查出数据库中真实的服务项列表，并转为 Map（ID：ServiceItem） 方便查询
        List<ServiceItem> dbServiceItems = serviceItemMapper.listByIds(serviceItemIds);
        Map<Long, ServiceItem> serviceItemMap = dbServiceItems.stream()
                .collect(Collectors.toMap(ServiceItem::getId, s -> s));

        // 核心计算与数据准备
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderServiceLogic> detailEntities = new ArrayList<>();
        List<OrderDetailVO> voServiceList = new ArrayList<>();

        for (OrderDetailDTO item : itemDTOs) {
            ServiceItem dbItem = serviceItemMap.get(item.getServiceItemId());
            if (dbItem == null) {
                throw new ServiceItemNotFoundException(ServiceItemConstant.SERVICE_ITEM_NOT_FOUND);
            }

            // 使用数据库中的单价，不使用前端传的 price
            BigDecimal unitPrice = BigDecimal.valueOf(dbItem.getPrice());
            BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
            totalAmount = totalAmount.add(subtotal);

            // 准备数据库实体
            detailEntities.add(OrderServiceLogic.builder()
                    .serviceItemId(item.getServiceItemId())
                    .quantity(item.getQuantity())
                    .unitPrice(dbItem.getPrice()) // 存储真实单价
                    .subtotalPrice(subtotal.doubleValue())
                    .createTime(now)
                    .build());

            // 准备 VO 返回数据
            voServiceList.add(OrderDetailVO.builder()
                    .serviceItemName(dbItem.getName()) // 数据库里的名称
                    .quantity(item.getQuantity())
                    .price(dbItem.getPrice())         // 数据库里的单价
                    .build());
        }

        // 插入订单主表（直接带入计算好的总价）
        Order order = Order.builder()
                .orderNo(orderNo)
                .userId(orderDTO.getUserId())
                .moveType(orderDTO.getMoveType())
                .startAddressId(orderDTO.getStartAddressId())
                .endAddressId(orderDTO.getEndAddressId())
                .totalAmount(totalAmount.doubleValue())
                .status(OrderStatusConstant.PENDING_PAY)
                .appointmentTime(orderDTO.getAppointmentTime())
                .createTime(now)
                .build();

        orderMapper.createOrder(order); // MyBatis 回填 ID

        // 批量插入详情表
        Long orderId = order.getId();
        detailEntities.forEach(d -> d.setOrderId(orderId));
        orderServiceLogicMapper.insertBatch(detailEntities);

        // 组装 VO 返回
        return assembleFinalVO(order, orderDTO, voServiceList);
    }

    // 组装最终的 VO
    private OrderVO assembleFinalVO(Order order, OrderDTO dto, List<OrderDetailVO> voItems) {
        UserAddress start = addressMapper.getNameById(dto.getStartAddressId());
        UserAddress end = addressMapper.getNameById(dto.getEndAddressId());

        return OrderVO.builder()
                .id(order.getId())
                .orderNo(order.getOrderNo())
                .moveType(order.getMoveType() == 1 ? "同城" : "跨城")
                .startAddressName(formatAddress(start))
                .endAddressName(formatAddress(end))
                .orderServiceList(voItems)
                .totalAmount(order.getTotalAmount())
                .appointmentTime(order.getAppointmentTime())
                .createTime(order.getCreateTime())
                .build();
    }

    // 格式化地址 --- 配合VO组装
    private String formatAddress(UserAddress addr) {
        if (addr == null) return "未知地址";
        return addr.getProvince() + addr.getCity() + addr.getDistrict() + addr.getDetailAddress();
    }
}