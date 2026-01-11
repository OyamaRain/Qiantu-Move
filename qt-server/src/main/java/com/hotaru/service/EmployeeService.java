package com.hotaru.service;

import com.hotaru.dto.EmployeeLoginDTO;
import com.hotaru.dto.EmployeePageQueryDTO;
import com.hotaru.entity.Employee;
import com.hotaru.result.PageResult;

public interface EmployeeService {

    //分页查询
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    //登录
    Employee login(EmployeeLoginDTO employeeLoginDTO);
}
