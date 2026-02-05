package com.hotaru.service.user.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotaru.constant.CommentStatusConstant;
import com.hotaru.constant.MessageConstant;
import com.hotaru.constant.OrderStatusConstant;
import com.hotaru.constant.ServiceItemConstant;
import com.hotaru.context.BaseContext;
import com.hotaru.dto.user.OrderCommentDTO;
import com.hotaru.dto.user.OrderDTO;
import com.hotaru.dto.user.OrderDetailDTO;
import com.hotaru.dto.user.OrderPageQueryDTO;
import com.hotaru.entity.*;
import com.hotaru.exception.*;
import com.hotaru.mapper.*;
import com.hotaru.result.PageResult;
import com.hotaru.service.user.UserOrderService;
import com.hotaru.utils.OrderUtils;
import com.hotaru.vo.user.OrderDetailVO;
import com.hotaru.vo.user.OrderVO;
import com.hotaru.vo.user.UserOrderDetailVO;
import com.hotaru.vo.user.UserOrderMoverVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    @Autowired
    private MoverMapper moverMapper;
    @Autowired
    private OrderCommentMapper orderCommentMapper;
    @Autowired
    private MoverCommentMapper moverCommentMapper;

    @Override
    @Transactional
    public OrderVO createOrder(OrderDTO orderDTO) {
        // 1. 获取当前登录用户 ID（从 ThreadLocal 中获取解析 token 后的 ID）
        Long currentUserId = BaseContext.getCurrentId();

        // 生成订单号
        String orderNo = OrderUtils.generateOrderSn();

        LocalDateTime now = LocalDateTime.now();
        List<OrderDetailDTO> itemDTOs = orderDTO.getOrderItems();

        // 提取 DTO 中的 ID
        List<Long> serviceItemIds = itemDTOs.stream()
                .map(OrderDetailDTO::getServiceItemId)
                .distinct()
                .collect(Collectors.toList());

        // 查出数据库中真实的服务项列表
        List<ServiceItem> dbServiceItems = serviceItemMapper.listByIds(serviceItemIds);
        Map<Long, ServiceItem> serviceItemMap = dbServiceItems.stream()
                .collect(Collectors.toMap(ServiceItem::getId, s -> s));

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderServiceLogic> detailEntities = new ArrayList<>();
        List<OrderDetailVO> voServiceList = new ArrayList<>();

        for (OrderDetailDTO item : itemDTOs) {
            ServiceItem dbItem = serviceItemMap.get(item.getServiceItemId());
            if (dbItem == null) {
                throw new ServiceItemNotFoundException(ServiceItemConstant.SERVICE_ITEM_NOT_FOUND);
            }

            // 使用数据库单价，确保金额安全
            BigDecimal unitPrice = BigDecimal.valueOf(dbItem.getPrice());
            BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
            totalAmount = totalAmount.add(subtotal);

            detailEntities.add(OrderServiceLogic.builder()
                    .serviceItemId(item.getServiceItemId())
                    .quantity(item.getQuantity())
                    .unitPrice(dbItem.getPrice())
                    .subtotalPrice(subtotal.doubleValue())
                    .createTime(now)
                    .build());

            voServiceList.add(OrderDetailVO.builder()
                    .serviceItemName(dbItem.getName())
                    .quantity(item.getQuantity())
                    .price(dbItem.getPrice())
                    .build());
        }

        // 2. 插入订单主表：使用从 BaseContext 获取的 currentUserId
        Order order = Order.builder()
                .orderNo(orderNo)
                .userId(currentUserId) // 此处已修改
                .moveType(orderDTO.getMoveType())
                .startAddressId(orderDTO.getStartAddressId())
                .endAddressId(orderDTO.getEndAddressId())
                .totalAmount(totalAmount.doubleValue())
                .status(OrderStatusConstant.PENDING_PAY)
                .appointmentTime(orderDTO.getAppointmentTime())
                .createTime(now)
                .isCommented(CommentStatusConstant.PENDING_COMMENTED)
                .build();

        orderMapper.createOrder(order);

        Long orderId = order.getId();
        detailEntities.forEach(d -> d.setOrderId(orderId));
        orderServiceLogicMapper.insertBatch(detailEntities);

        return assembleFinalVO(order, orderDTO, voServiceList);
    }

    @Override
    public void payOrder(Long orderId) {
        Long currentUserId = BaseContext.getCurrentId();

        // 1. 查询订单
        Order orders = orderMapper.getById(orderId);
        if (orders == null) {
            throw new OrderNotFoundException(MessageConstant.ORDER_NOT_FOUND);
        }

        // 2. 校验订单归属
        if (!orders.getUserId().equals(currentUserId)) {
            throw new OrderAuthErrorException(MessageConstant.ORDER_AUTH_ERROR);
        }

        // 3. 校验订单状态
        if (!OrderStatusConstant.PENDING_PAY.equals(orders.getStatus())) {
            throw new OrderPayStatusErrorException(MessageConstant.ORDER_PAYMENT_FAILED);
        }

        // 4. 模拟支付成功，更新订单
        //TODO 支付成功后，调用第三方支付接口
        Order updateOrder = new Order();
        updateOrder.setId(orderId);
        updateOrder.setStatus(OrderStatusConstant.PAID);
        updateOrder.setUpdateTime(LocalDateTime.now());

        orderMapper.update(updateOrder);
    }

    @Override
    public PageResult pageQuery(OrderPageQueryDTO orderPageQueryDTO) {
        // 1. 设置默认值，防止 Page 或 PageSize 为 null 导致崩溃
        int pageNum = (orderPageQueryDTO.getPage() == null) ? 1 : orderPageQueryDTO.getPage();
        int pageSize = (orderPageQueryDTO.getPageSize() == null) ? 10 : orderPageQueryDTO.getPageSize();

        // 2. 开启分页
        PageHelper.startPage(pageNum, pageSize);

        // 3. 执行查询
        Page<Order> page = orderMapper.userPageQuery(orderPageQueryDTO);

        // 4. 封装返回
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public UserOrderDetailVO getDetailsById(Long orderId) {
        // 1. 获取当前登录用户，防止水平越权
        Long userId = BaseContext.getCurrentId();

        // 2. 一次性联表查询订单、师傅、地址原始信息
        UserOrderDetailVO detailVO = orderMapper.getDetailWithRelations(orderId, userId);

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

    @Override
    public void cancelOrder(Long orderId) {
        Long currentUserId = BaseContext.getCurrentId();

        // 1. 查询订单
        Order orders = orderMapper.getById(orderId);
        if (orders == null) {
            throw new OrderNotFoundException(MessageConstant.ORDER_NOT_FOUND);
        }

        // 2. 校验订单归属
        if (!orders.getUserId().equals(currentUserId)) {
            throw new OrderAuthErrorException(MessageConstant.ORDER_AUTH_ERROR);
        }

        // 3. 校验订单状态
        Integer status = orders.getStatus();
        Integer isCommented = orders.getIsCommented();
        if (OrderStatusConstant.ASSIGNED.equals(status)
                || OrderStatusConstant.COMPLETED.equals(status)
                || OrderStatusConstant.CANCELLED.equals(status)
                || CommentStatusConstant.COMMENTED.equals(isCommented)) {
            throw new OrderStatusErrorException(MessageConstant.ORDER_CANNOT_CANCELLED);
        }

        // 4. 更新订单状态
        Order updateOrder = new Order();
        updateOrder.setId(orderId);
        updateOrder.setStatus(OrderStatusConstant.CANCELLED);
        updateOrder.setUpdateTime(LocalDateTime.now());

        orderMapper.update(updateOrder);
    }

    @Override
    public UserOrderMoverVO getMoverInfo(Long orderId) {
        Long currentUserId = BaseContext.getCurrentId();

        // 1. 查询订单
        Order order = orderMapper.getById(orderId);
        if (order == null) {
            throw new OrderNotFoundException(MessageConstant.ORDER_NOT_FOUND);
        }

        // 2. 校验订单归属
        if (!order.getUserId().equals(currentUserId)) {
            throw new OrderAuthErrorException(MessageConstant.ORDER_AUTH_ERROR);
        }

        // 3. 校验订单状态
        Integer status = order.getStatus();
        Integer isCommented = order.getIsCommented();
        if (!OrderStatusConstant.COMPLETED.equals(status)
                || CommentStatusConstant.COMMENTED.equals(isCommented)) {
            throw new OrderStatusErrorException(MessageConstant.ORDER_CANNOT_COMMENT_BEFORE_FINISH);
        }

        Mover mover = orderMapper.getMoverInfoByOrderId(orderId);
        if (mover == null) {
            throw new MoverNotFoundException(MessageConstant.MOVER_NOT_FOUND);
        }
        return UserOrderMoverVO.builder()
               .moverId(mover.getId())
               .avatar(mover.getAvatar())
               .moverName(mover.getName())
               .build();
    }

    @Override
    @Transactional
    public void commentOrder(OrderCommentDTO orderCommentDTO) {
        Long currentUserId = BaseContext.getCurrentId();

        // 查询订单
        Order order = orderMapper.getById(orderCommentDTO.getOrderId());
        if (order == null) {
            throw new OrderNotFoundException(MessageConstant.ORDER_NOT_FOUND);
        }

        // 校验订单归属
        if (!order.getUserId().equals(currentUserId)) {
            throw new OrderAuthErrorException(MessageConstant.ORDER_AUTH_ERROR);
        }

        // 校验订单状态
        if (!OrderStatusConstant.COMPLETED.equals(order.getStatus())) {
            throw new OrderStatusErrorException(MessageConstant.ORDER_CANNOT_COMMENT_BEFORE_FINISH);
        }

        // 校验是否已评价
        if (order.getIsCommented() == 1) {
            throw new OrderStatusErrorException(MessageConstant.ORDER_CANNOT_COMMENT_TWICE);
        }

        Long moverId = order.getMoverId();

        // ================= 订单评价 =================
        OrderComment orderComment = new OrderComment();
        orderComment.setOrderId(order.getId());
        orderComment.setUserId(currentUserId);
        orderComment.setScore(orderCommentDTO.getOrderScore());
        orderComment.setContent(orderCommentDTO.getOrderContent());
        orderComment.setCreateTime(LocalDateTime.now());

        orderCommentMapper.insert(orderComment);

        // ================= 师傅评价 =================
        MoverComment moverComment = new MoverComment();
        moverComment.setMoverId(moverId);
        moverComment.setOrderId(order.getId());
        moverComment.setUserId(currentUserId);
        moverComment.setScore(orderCommentDTO.getMoverScore());
        moverComment.setContent(orderCommentDTO.getMoverContent());
        moverComment.setCreateTime(LocalDateTime.now());

        moverCommentMapper.insert(moverComment);

        // ================= 更新师傅评分（行锁） =================
        Mover mover = moverMapper.getById(moverId);

        int oldCount = mover.getOrderCount();
        BigDecimal oldRating = BigDecimal.valueOf(mover.getRating());

        BigDecimal newRating = oldRating
                .multiply(BigDecimal.valueOf(oldCount))
                .add(BigDecimal.valueOf(orderCommentDTO.getMoverScore()))
                .divide(BigDecimal.valueOf(oldCount + 1), 2, RoundingMode.HALF_UP);

        moverMapper.updateRating(
                moverId,
                newRating,
                oldCount + 1
        );

        // ================= 标记订单已评价 =================
        orderMapper.markCommented(order.getId());
    }

    //-------------------------------------------------------------------------------------
    // 用于组装下单返回的VO
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

    //查询师傅信息
    public UserOrderMoverVO getMoverBasicInfo(Long orderId) {
        // 直接查数据库，不判断订单状态
        Mover mover = orderMapper.getMoverInfoByOrderId(orderId);
        if (mover == null) {
            throw new MoverNotFoundException(MessageConstant.MOVER_NOT_FOUND);
        }
        return UserOrderMoverVO.builder()
                .moverId(mover.getId())
                .moverName(mover.getName())
                .build();
    }
}