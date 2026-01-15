package com.hotaru.controller.admin;

import com.hotaru.constant.JwtClaimsConstant;
import com.hotaru.constant.StatusConstant;
import com.hotaru.dto.EmployeeDTO;
import com.hotaru.dto.EmployeeLoginDTO;
import com.hotaru.dto.EmployeePageQueryDTO;
import com.hotaru.dto.EmployeePasswordDTO;
import com.hotaru.entity.Employee;
import com.hotaru.properties.JwtProperties;
import com.hotaru.result.PageResult;
import com.hotaru.result.Result;
import com.hotaru.service.EmployeeService;
import com.hotaru.utils.JwtUtil;
import com.hotaru.vo.EmployeeLoginVO;
import com.hotaru.vo.EmployeeVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/login")
    @Tag(name = "员工管理")
    @Operation(summary = "员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO  employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        claims.put(JwtClaimsConstant.ROLES, employee.getRole());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    @GetMapping("/page")
    @Tag(name = "员工管理")
    @Operation(summary = "员工分页查询")
    public Result<PageResult> page(@ParameterObject EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("分页查询员工信息:{}", employeePageQueryDTO);
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    @PutMapping("/editPassword")
    @Tag(name = "员工管理")
    @Operation(summary = "员工修改密码")
    public Result editPassword(@RequestBody EmployeePasswordDTO employeePasswordDTO) {
        log.info("修改密码:{}", employeePasswordDTO);
        employeeService.editPassword(employeePasswordDTO);
        return Result.success();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/status/{status}")
    @Tag(name = "员工管理")
    @Operation(summary = "启用或禁用员工账号状态")
    public Result status(@PathVariable Integer status, @RequestParam Long id) {
        log.info("员工ID{}账号状态:{}", id, status == StatusConstant.ENABLE ? "启用" : "禁用");
        employeeService.status(status, id);
        return Result.success();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Tag(name = "员工管理")
    @Operation(summary = "新增员工")
    public Result addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        log.info("新增员工:{}", employeeDTO);
        employeeService.addEmployee(employeeDTO);
        return Result.success();
    }

    @GetMapping("/{id}")
    @Tag(name = "员工管理")
    @Operation(summary = "根据ID查询员工信息")
    public Result<EmployeeVO> getEmployeeById(@PathVariable Long id){
        log.info("根据id查询员工{}的信息", id);
        EmployeeVO employeeVO = employeeService.getById(id);
        return Result.success(employeeVO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    @Tag(name = "员工管理")
    @Operation(summary = "编辑员工信息")
    public Result updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        log.info("编辑员工信息:{}", employeeDTO);
        employeeService.updateEmployee(employeeDTO);
        return Result.success();
    }
}
