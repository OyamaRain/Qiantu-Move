package com.hotaru.mapper;

import com.github.pagehelper.Page;
import com.hotaru.dto.OrderPageQueryDTO;
import com.hotaru.entity.Order;
import com.hotaru.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapper {
    OrderVO getDetailsById(Long id);

    Order getById(Long id);

    Page<Order> pageQuery(OrderPageQueryDTO orderPageQueryDTO);

    void update(Order order);


}
