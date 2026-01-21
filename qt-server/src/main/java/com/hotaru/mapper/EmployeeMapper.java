package com.hotaru.mapper;

import com.github.pagehelper.Page;
import com.hotaru.dto.EmployeePageQueryDTO;
import com.hotaru.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    @Select("select * from employee where id = #{empId}")
    Employee getById(Long empId);

    void update(Employee employee);

    @Insert("insert into employee (username, password, name,role, sex, phone, avatar, status, create_time)" +
            " values (#{username}, #{password}, #{name},#{role}, #{sex}, #{phone}, #{avatar}, #{status}, #{createTime}) ")
    void insert(Employee employee);
}
