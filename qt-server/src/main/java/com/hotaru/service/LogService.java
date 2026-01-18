package com.hotaru.service;

import com.hotaru.dto.LogPageQueryDTO;
import com.hotaru.result.PageResult;

public interface LogService {
    // 分页查询
    PageResult pageQuery(LogPageQueryDTO logPageQueryDTO);
}
