package com.hotaru.mapper;

import com.github.pagehelper.Page;
import com.hotaru.dto.admin.ServiceItemPageQueryDTO;
import com.hotaru.entity.ServiceItem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ServiceItemMapper {

    Page<ServiceItem> pageQuery(ServiceItemPageQueryDTO serviceItemPageQueryDTO);

    void update(ServiceItem serviceItem);

    @Select("select * from service_item where id = #{id}")
    ServiceItem getById(Long id);

    void delete(List<Long> ids);

    @Insert("insert into service_item (name, category_id, unit, price, status, create_time, update_time) " +
            "values (#{name}, #{categoryId}, #{unit}, #{price}, #{status}, #{createTime}, #{updateTime})")
    void insert(ServiceItem serviceItem);

    @Select("select * from service_item where category_id = #{CategoryId}")
    List<ServiceItem> getByCategoryId(Long CategoryId);

    @Delete("delete from service_item where category_id = #{CategoryId}")
    void deleteByCategoryId(Long CategoryId);
}
