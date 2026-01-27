package com.hotaru.service.admin.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotaru.constant.ServiceCategoryConstant;
import com.hotaru.constant.ServiceItemConstant;
import com.hotaru.constant.StatusConstant;
import com.hotaru.dto.admin.ServiceCategoryDTO;
import com.hotaru.dto.admin.ServiceCategoryPageQueryDTO;
import com.hotaru.entity.ServiceCategory;
import com.hotaru.entity.ServiceItem;
import com.hotaru.exception.ServiceCategoryStatusException;
import com.hotaru.exception.ServiceItemStatusException;
import com.hotaru.mapper.ServiceCategoryMapper;
import com.hotaru.mapper.ServiceItemMapper;
import com.hotaru.result.PageResult;
import com.hotaru.service.admin.AdminServiceCategoryService;
import com.hotaru.vo.admin.ServiceCategoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminAdminServiceCategoryServiceImpl implements AdminServiceCategoryService {

    @Autowired
    private ServiceCategoryMapper serviceCategoryMapper;
    @Autowired
    private ServiceItemMapper serviceItemMapper;

    @Override
    public void update(ServiceCategoryDTO serviceCategoryDTO) {
        // 封装参数
        ServiceCategory serviceCategory = new ServiceCategory();
        BeanUtils.copyProperties(serviceCategoryDTO,serviceCategory);
        serviceCategory.setUpdateTime(LocalDateTime.now());

        // 调用Mapper方法
        serviceCategoryMapper.update(serviceCategory);
    }

    @Override
    public PageResult pageQuery(ServiceCategoryPageQueryDTO serviceCategoryPageQueryDTO) {
        // 开始分页
        PageHelper.startPage(serviceCategoryPageQueryDTO.getPage(),serviceCategoryPageQueryDTO.getPageSize());

        // 调用Mapper方法
        Page<ServiceCategory> page = serviceCategoryMapper.pageQuery(serviceCategoryPageQueryDTO);

        // 封装结果
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void EnableOrDisable(Integer status, Long id) {
        // 根据ID查询分类
        ServiceCategory serviceCategory = serviceCategoryMapper.getById(id);

        // 判断该分类是否包含已开启的服务,如果包含则不允许操作
        List<ServiceItem> serviceItemList = serviceItemMapper.getByCategoryId(serviceCategory.getId());
        for (ServiceItem serviceItem : serviceItemList) {
            if(serviceItem.getStatus() == StatusConstant.ENABLE){
                throw new ServiceCategoryStatusException(ServiceCategoryConstant.SERVICE_CATEGORY_STATUS_ERROR);
            }
        }

        // 修改分类状态
        serviceCategory.setStatus(status);
        serviceCategory.setUpdateTime(LocalDateTime.now());
        serviceCategoryMapper.update(serviceCategory);
    }

    @Override
    public void add(ServiceCategoryDTO serviceCategoryDTO) {
        // 封装参数
        ServiceCategory serviceCategory = new ServiceCategory();
        BeanUtils.copyProperties(serviceCategoryDTO,serviceCategory);

        // 默认设置状态为禁用
        serviceCategory.setStatus(StatusConstant.DISABLE);
        serviceCategory.setCreateTime(LocalDateTime.now());

        // 调用Mapper方法
        serviceCategoryMapper.insert(serviceCategory);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // 根据ID查询分类
        ServiceCategory serviceCategory = serviceCategoryMapper.getById(id);

        // 判断该分类状态是否为启用,如果为启用则不允许操作
        if(serviceCategory.getStatus() == StatusConstant.ENABLE){
            throw new ServiceCategoryStatusException(ServiceCategoryConstant.SERVICE_CATEGORY_STATUS_ERROR);
        }

        // 判断该分类是否包含已启用的服务项,如果包含则不允许操作
        List<ServiceItem> serviceItemList = serviceItemMapper.getByCategoryId(serviceCategory.getId());
        for (ServiceItem serviceItem : serviceItemList) {
            if(serviceItem.getStatus() == StatusConstant.ENABLE){
                throw new ServiceItemStatusException(ServiceItemConstant.SERVICE_ITEM_STATUS_ERROR);
            }
        }

        // 删除分类关联的服务项
        serviceItemMapper.deleteByCategoryId(id);

        // 删除分类
        serviceCategoryMapper.delete(id);


    }

    @Override
    public ServiceCategoryVO getByCategoryId(Long id) {
        // 调用Mapper方法
        ServiceCategory serviceCategory = serviceCategoryMapper.getById(id);

        // 封装结果
        ServiceCategoryVO serviceCategoryVO = new ServiceCategoryVO();
        BeanUtils.copyProperties(serviceCategory,serviceCategoryVO);
        return serviceCategoryVO;
    }
}
