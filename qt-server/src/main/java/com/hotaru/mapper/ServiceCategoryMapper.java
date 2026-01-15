package com.hotaru.mapper;

import com.github.pagehelper.Page;
import com.hotaru.dto.ServiceCategoryPageQueryDTO;
import com.hotaru.entity.ServiceCategory;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ServiceCategoryMapper {

    void update(ServiceCategory serviceCategory);

    Page<ServiceCategory> pageQuery(ServiceCategoryPageQueryDTO serviceCategoryPageQueryDTO);

    @Select("select * from service_category where id = #{id}")
    ServiceCategory getById(Long id);

    @Insert("insert into service_category (name, sort, status, create_time) VALUES (#{name}, #{sort}, #{status}, #{createTime})")
    void insert(ServiceCategory serviceCategory);

    @Delete("delete from service_category where id = #{id}")
    void delete(Long id);
}
