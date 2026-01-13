package com.hotaru.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotaru.constant.ServiceItemConstant;
import com.hotaru.constant.StatusConstant;
import com.hotaru.dto.ServiceItemDTO;
import com.hotaru.dto.ServiceItemPageQueryDTO;
import com.hotaru.entity.ServiceItem;
import com.hotaru.exception.ServiceItemNotFoundException;
import com.hotaru.exception.ServiceItemStatusException;
import com.hotaru.mapper.ServiceItemMapper;
import com.hotaru.result.PageResult;
import com.hotaru.service.ServiceItemService;
import com.hotaru.vo.ServiceItemVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServiceItemServiceImpl implements ServiceItemService {

    @Autowired
    private ServiceItemMapper serviceItemMapper;

    @Override
    public PageResult pageQuery(ServiceItemPageQueryDTO serviceItemPageQueryDTO) {
        // 调用分页查询方法
        PageHelper.startPage(serviceItemPageQueryDTO.getPage(), serviceItemPageQueryDTO.getPageSize());

        // 调用Mapper查询数据
        Page<ServiceItem> page = serviceItemMapper.pageQuery(serviceItemPageQueryDTO);

        // 获取查询结果的总记录数
        long total = page.getTotal();
        List<ServiceItem> result = page.getResult();

        // 返回分页查询结果
        return new PageResult(total, result);
    }

    @Override
    public void update(ServiceItemDTO serviceItemDTO) {

        // 封装ServiceItem对象
        ServiceItem serviceItem = new ServiceItem();
        BeanUtils.copyProperties(serviceItemDTO,serviceItem);
        serviceItem.setUpdateTime(LocalDateTime.now());

        // 调用Mapper更新数据
        serviceItemMapper.update(serviceItem);
    }

    @Override
    public void delete(List<Long> ids) {

        // 历遍ids列表
        for (Long id : ids) {
            ServiceItem serviceItem = serviceItemMapper.getById(id);

            // 判断服务项是否存在
            if(serviceItem == null){
                throw new ServiceItemNotFoundException(ServiceItemConstant.SERVICE_ITEM_NOT_FOUND);
            }

            // 判断状态
            if(serviceItem.getStatus() == StatusConstant.ENABLE){
                throw new ServiceItemStatusException(ServiceItemConstant.SERVICE_ITEM_STATUS_ERROR);
            }

            // 调用Mapper删除数据
            serviceItemMapper.delete(id);
        }
    }

    @Override
    public void EnableOrDisable(Integer status, Long id) {

        // 调用Mapper查询服务项
        ServiceItem serviceItem = serviceItemMapper.getById(id);

        // 封装ServiceItem对象
        serviceItem.setStatus(status);
        serviceItem.setUpdateTime(LocalDateTime.now());

        // 调用Mapper更新数据
        serviceItemMapper.update(serviceItem);

    }

    @Override
    public void add(ServiceItemDTO serviceItemDTO) {
        // 封装ServiceItem对象
        ServiceItem serviceItem = new ServiceItem();
        BeanUtils.copyProperties(serviceItemDTO,serviceItem);

        // 默认设置禁用状态
        serviceItem.setStatus(StatusConstant.DISABLE);
        serviceItem.setCreateTime(LocalDateTime.now());

        // 调用Mapper插入数据
        serviceItemMapper.insert(serviceItem);

    }

    @Override
    public ServiceItemVO getById(Long id) {
        ServiceItem serviceItem = serviceItemMapper.getById(id);
        ServiceItemVO serviceItemVO = new ServiceItemVO();
        BeanUtils.copyProperties(serviceItem,serviceItemVO);
        return serviceItemVO;
    }
}
