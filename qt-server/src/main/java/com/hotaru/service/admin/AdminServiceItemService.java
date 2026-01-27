package com.hotaru.service.admin;

import com.hotaru.dto.admin.ServiceItemDTO;
import com.hotaru.dto.admin.ServiceItemPageQueryDTO;
import com.hotaru.result.PageResult;
import com.hotaru.vo.admin.ServiceItemVO;

import java.util.List;

public interface AdminServiceItemService {
    //分页查询
    PageResult pageQuery(ServiceItemPageQueryDTO serviceItemPageQueryDTO);

    //新增服务项
    void update(ServiceItemDTO serviceItemDTO);

    //批量删除
    void delete(List<Long> ids);

    //开启或关闭服务项
    void EnableOrDisable(Integer status, Long id);

    //新增服务项
    void add(ServiceItemDTO serviceItemDTO);

    //根据id查询服务项
    ServiceItemVO getById(Long id);
}
