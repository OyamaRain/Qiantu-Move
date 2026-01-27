package com.hotaru.mapper;

import com.github.pagehelper.Page;
import com.hotaru.dto.admin.OrderPageQueryDTO;
import com.hotaru.entity.Order;
import com.hotaru.vo.admin.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {
    OrderVO getDetailsById(Long id);

    Order getById(Long id);

    Page<Order> pageQuery(OrderPageQueryDTO orderPageQueryDTO);

    void update(Order order);

    //----------------------------------------------

    void createOrder(Order order);

    @Update("UPDATE orders SET total_amount = #{totalAmount} WHERE id = #{id}")
    void updateTotalAmount(Long id, double totalAmount);

    List<Long> selectTimeoutIds(LocalDateTime limitTime, Integer status);

    void batchCancel(List<Long> ids, Integer status);
}
