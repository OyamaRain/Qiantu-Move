package com.hotaru.mapper;

import com.github.pagehelper.Page;
import com.hotaru.dto.admin.LogPageQueryDTO;
import com.hotaru.entity.SysLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper {

    @Insert("insert into sys_log (employee_id, employee_name, operation, method, create_time) " +
            "values (#{employeeId}, #{employeeName}, #{operation}, #{method}, #{createTime})")
    void insert(SysLog sysLog);

    Page<SysLog> pageQuery(LogPageQueryDTO logPageQueryDTO);
}
