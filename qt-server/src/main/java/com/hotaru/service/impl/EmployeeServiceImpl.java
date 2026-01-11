package com.hotaru.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotaru.constant.MessageConstant;
import com.hotaru.constant.StatusConstant;
import com.hotaru.dto.EmployeeLoginDTO;
import com.hotaru.dto.EmployeePageQueryDTO;
import com.hotaru.entity.Employee;
import com.hotaru.exception.AccountLockedException;
import com.hotaru.exception.AccountNotFoundException;
import com.hotaru.exception.PasswordErrorException;
import com.hotaru.mapper.EmployeeMapper;
import com.hotaru.result.PageResult;
import com.hotaru.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        //对前端的密码进行MD5加密处理
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        //开始分页查询
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
        //执行查询操作，获取到page对象
        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);
        //获取总记录数
        long total = page.getTotal();
        //获取当前页数据集合
        List<Employee> records = page.getResult();
        //封装并返回
        return new PageResult(total, records);
    }


}
