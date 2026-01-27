package com.hotaru.mapper;

import com.hotaru.entity.OrderServiceLogic;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderServiceLogicMapper {

    void insertBatch(List<OrderServiceLogic> detailList);
}
