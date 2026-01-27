package com.hotaru.service.admin;

import com.hotaru.dto.admin.ServiceCategoryDTO;
import com.hotaru.dto.admin.ServiceCategoryPageQueryDTO;
import com.hotaru.result.PageResult;
import com.hotaru.vo.admin.ServiceCategoryVO;

public interface AdminServiceCategoryService {
    // 修改分类
    void update(ServiceCategoryDTO serviceCategoryDTO);

    // 分页查询分类
    PageResult pageQuery(ServiceCategoryPageQueryDTO serviceCategoryPageQueryDTO);

    // 启用、禁用分类
    void EnableOrDisable(Integer status, Long id);

    // 新增分类
    void add(ServiceCategoryDTO serviceCategoryDTO);

    // 根据ID删除分类
    void delete(Long id);

    // 根据ID查询分类
    ServiceCategoryVO getByCategoryId(Long id);
}
