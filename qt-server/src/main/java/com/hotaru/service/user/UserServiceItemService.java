package com.hotaru.service.user;

import com.hotaru.dto.user.ServiceItemPageQueryDTO;
import com.hotaru.result.PageResult;

public interface UserServiceItemService {
    // 分页查询
    PageResult pageQuery(ServiceItemPageQueryDTO serviceItemPageQueryDTO);
}
