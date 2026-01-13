package com.hotaru.service;

import com.hotaru.dto.ServiceItemDTO;
import com.hotaru.dto.ServiceItemPageQueryDTO;
import com.hotaru.result.PageResult;
import com.hotaru.vo.ServiceItemVO;

import java.util.List;

public interface ServiceItemService {
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
