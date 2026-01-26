package com.hotaru.service.admin;

import com.hotaru.dto.admin.LogPageQueryDTO;
import com.hotaru.result.PageResult;

public interface LogService {
    // 分页查询
    PageResult pageQuery(LogPageQueryDTO logPageQueryDTO);
}
