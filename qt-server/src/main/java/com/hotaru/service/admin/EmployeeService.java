package com.hotaru.service.admin;

import com.hotaru.dto.admin.EmployeeDTO;
import com.hotaru.dto.admin.EmployeeLoginDTO;
import com.hotaru.dto.admin.EmployeePageQueryDTO;
import com.hotaru.dto.admin.EmployeePasswordDTO;
import com.hotaru.entity.Employee;
import com.hotaru.result.PageResult;
import com.hotaru.vo.admin.EmployeeVO;

public interface EmployeeService {

    //分页查询
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    //登录
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    //修改密码
    void editPassword(EmployeePasswordDTO employeePasswordDTO);

    //启用禁用员工账号
    void status(Integer status, Long id);

    //新增员工
    void addEmployee(EmployeeDTO employeeDTO);

    //根据id查询员工信息
    EmployeeVO getById(Long id);

    //编辑员工信息
    void updateEmployee(EmployeeDTO employeeDTO);
}
