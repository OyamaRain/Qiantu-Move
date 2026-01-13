package com.hotaru.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotaru.constant.IdentityConstant;
import com.hotaru.constant.MessageConstant;
import com.hotaru.constant.PasswordConstant;
import com.hotaru.constant.StatusConstant;
import com.hotaru.context.BaseContext;
import com.hotaru.dto.EmployeeDTO;
import com.hotaru.dto.EmployeeLoginDTO;
import com.hotaru.dto.EmployeePageQueryDTO;
import com.hotaru.dto.EmployeePasswordDTO;
import com.hotaru.entity.Employee;
import com.hotaru.exception.AccountLockedException;
import com.hotaru.exception.AccountNotFoundException;
import com.hotaru.exception.IdentityErrorException;
import com.hotaru.exception.PasswordErrorException;
import com.hotaru.mapper.EmployeeMapper;
import com.hotaru.result.PageResult;
import com.hotaru.service.EmployeeService;
import com.hotaru.vo.EmployeeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
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

    @Override
    public void editPassword(EmployeePasswordDTO employeePasswordDTO) {
        // 根据id查询员工信息
        Employee employee = employeeMapper.getById(employeePasswordDTO.getEmpId());
        String oldPassword = employeePasswordDTO.getOldPassword();
        String newPassword = employeePasswordDTO.getNewPassword();

        //将旧密码进行MD5加密后再对比
        oldPassword = DigestUtils.md5DigestAsHex(oldPassword.getBytes());

        // 比对旧密码是否正确
        if(!oldPassword.equals(employee.getPassword())){
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        // 对新密码进行加密处理
        employee.setPassword(DigestUtils.md5DigestAsHex(newPassword.getBytes()));

        // 调用mapper更新数据
        employee.setUpdateTime(LocalDateTime.now());
        employeeMapper.update(employee);
    }

    @Override
    public void status(Integer status, Long id) {
        // 根据id查询员工信息
        Employee employee = employeeMapper.getById(id);

        // 校验当前身份权限
        Long currentId = BaseContext.getCurrentId();
        if(currentId != IdentityConstant.ADMIN){
            throw new IdentityErrorException(MessageConstant.IDENTITY_ERROR);
        }

        // 调用mapper更新数据
        employee.setStatus(status);
        employee.setUpdateTime(LocalDateTime.now());
        employeeMapper.update(employee);
    }

    @Override
    public void addEmployee(EmployeeDTO employeeDTO) {
        // 对默认密码进行MD5加密处理
        String password = PasswordConstant.DEFAULT_PASSWORD;
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        
        // 封装数据
        Employee employee = Employee.builder()
                .username(employeeDTO.getUsername())
                .password(password)
                .name(employeeDTO.getName())
                .sex(employeeDTO.getSex())
                .phone(employeeDTO.getPhone())
                .avatar(employeeDTO.getAvatar())
                .status(StatusConstant.ENABLE)
                .createTime(LocalDateTime.now())
                .build();

        // 调用Mapper插入数据
        employeeMapper.insert(employee);
    }

    @Override
    public EmployeeVO getById(Long id) {
        Employee employee = employeeMapper.getById(id);
        EmployeeVO employeeVO = new EmployeeVO();
        BeanUtils.copyProperties(employee, employeeVO);
        return employeeVO;
    }

    @Override
    public void updateEmployee(EmployeeDTO employeeDTO) {
        // 封装数据
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        // 调用Mapper更新数据
        employee.setUpdateTime(LocalDateTime.now());
        employeeMapper.update(employee);
    }


}
