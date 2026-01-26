package com.hotaru.service.admin.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotaru.dto.admin.LogPageQueryDTO;
import com.hotaru.entity.SysLog;
import com.hotaru.mapper.LogMapper;
import com.hotaru.result.PageResult;
import com.hotaru.service.admin.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public PageResult pageQuery(LogPageQueryDTO logPageQueryDTO) {
        // 开始分页
        PageHelper.startPage(logPageQueryDTO.getPage(), logPageQueryDTO.getPageSize());

        // 执行查询
        Page<SysLog> page = logMapper.pageQuery(logPageQueryDTO);

        long total = page.getTotal();
        List<SysLog> result = page.getResult();

        // 封装结果
        return new PageResult(total, result);
    }
}
