package com.hotaru.service.user.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotaru.dto.user.ServiceItemPageQueryDTO;
import com.hotaru.entity.Order;
import com.hotaru.entity.ServiceItem;
import com.hotaru.mapper.ServiceItemMapper;
import com.hotaru.result.PageResult;
import com.hotaru.service.user.UserServiceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceItemServiceImpl implements UserServiceItemService {

    @Autowired
    private ServiceItemMapper serviceItemMapper;

    @Override
    public PageResult pageQuery(ServiceItemPageQueryDTO serviceItemPageQueryDTO) {
        // 1. 设置默认值，防止 Page 或 PageSize 为 null 导致崩溃
        int pageNum = (serviceItemPageQueryDTO.getPage() == null) ? 1 : serviceItemPageQueryDTO.getPage();
        int pageSize = (serviceItemPageQueryDTO.getPageSize() == null) ? 10 : serviceItemPageQueryDTO.getPageSize();

        // 2. 开启分页
        PageHelper.startPage(pageNum, pageSize);

        // 3. 执行查询
        Page<ServiceItem> page = serviceItemMapper.userServiceItemPageQuery(serviceItemPageQueryDTO);

        // 4. 封装返回
        return new PageResult(page.getTotal(), page.getResult());
    }
}
