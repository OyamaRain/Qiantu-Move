package com.hotaru.mapper;

import com.hotaru.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapper {

    @Select("select * from orders where id = #{id}")
    OrderVO getById(Long id);

}
