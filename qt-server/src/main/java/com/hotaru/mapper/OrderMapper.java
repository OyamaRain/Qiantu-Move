package com.hotaru.mapper;

import com.github.pagehelper.Page;
import com.hotaru.dto.admin.OrderPageQueryDTO;
import com.hotaru.entity.Mover;
import com.hotaru.entity.Order;
import com.hotaru.vo.admin.OrderVO;
import com.hotaru.vo.user.UserOrderDetailVO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
    OrderVO getDetailsById(Long id);

    Order getById(Long id);

    Page<Order> pageQuery(OrderPageQueryDTO orderPageQueryDTO);

    void update(Order order);

    //----------------------------------------------

    void createOrder(Order order);

    List<Long> selectTimeoutIds(LocalDateTime limitTime, Integer status);

    void batchCancel(List<Long> ids, Integer status);

    Page<Order> userPageQuery(com.hotaru.dto.user.OrderPageQueryDTO orderPageQueryDTO);

    UserOrderDetailVO getDetailWithRelations(Long orderId, Long userId);

    Mover getMoverInfoByOrderId(Long orderId);

    void markCommented(Long orderId);

    Map<String, Object> getOrderCountById(Long userId);
}
