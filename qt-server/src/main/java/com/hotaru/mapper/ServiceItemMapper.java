package com.hotaru.mapper;

import com.github.pagehelper.Page;
import com.hotaru.dto.ServiceItemPageQueryDTO;
import com.hotaru.entity.ServiceItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ServiceItemMapper {

    Page<ServiceItem> pageQuery(ServiceItemPageQueryDTO serviceItemPageQueryDTO);

    void update(ServiceItem serviceItem);

    @Select("select * from service_item where id = #{id}")
    ServiceItem getById(Long id);

    @Delete("delete from service_item where id = #{id}")
    void delete(Long id);

    @Insert("insert into service_item (name, type, unit, price, status, create_time, update_time) " +
            "values (#{name}, #{type}, #{unit}, #{price}, #{status}, #{createTime}, #{updateTime})")
    void insert(ServiceItem serviceItem);
}
