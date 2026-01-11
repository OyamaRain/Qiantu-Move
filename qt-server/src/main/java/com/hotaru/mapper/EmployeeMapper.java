package com.hotaru.mapper;

import com.github.pagehelper.Page;
import com.hotaru.dto.EmployeePageQueryDTO;
import com.hotaru.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);
}
