package com.hotaru.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotaru.dto.MoverDTO;
import com.hotaru.dto.MoverPageQueryDTO;
import com.hotaru.entity.Mover;
import com.hotaru.mapper.MoverMapper;
import com.hotaru.result.PageResult;
import com.hotaru.service.MoverService;
import com.hotaru.vo.MoverVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MoverServiceImpl implements MoverService {

    @Autowired
    private MoverMapper moverMapper;

    @Override
    public PageResult pageQuery(MoverPageQueryDTO moverPageQueryDTO) {
        // 开始分页查询
        PageHelper.startPage(moverPageQueryDTO.getPage(), moverPageQueryDTO.getPageSize());

        // 调用Mapper查询数据
        Page<Mover> page = moverMapper.pageQuery(moverPageQueryDTO);

        // 获取分页查询结果
        long total = page.getTotal();
        List<Mover> result = page.getResult();

        // 返回分页结果
        return new PageResult(total, result);

    }

    @Override
    public void EnableOrDisable(Integer status, Long id) {
        // 根据ID查询搬家师傅信息
        Mover mover = moverMapper.getById(id);

        // 设置新的状态
        mover.setStatus(status);

        // 更新搬家师傅信息
        mover.setUpdateTime(LocalDateTime.now());
        moverMapper.update(mover);
    }

    @Override
    public MoverVO getMoverById(Long id) {
        Mover mover = moverMapper.getById(id);
        MoverVO moverVO = new MoverVO();
        BeanUtils.copyProperties(mover, moverVO);
        return moverVO;
    }

    @Override
    public void update(MoverDTO moverDTO) {
        // 数据封装
        Mover mover = Mover.builder()
                .id(moverDTO.getId())
                .name(moverDTO.getName())
                .phone(moverDTO.getPhone())
                .avatar(moverDTO.getAvatar())
                .rating(moverDTO.getRating())
                .orderCount(moverDTO.getOrderCount())
                .updateTime(LocalDateTime.now())
                .build();

        // 调用Mapper更新数据
        moverMapper.update(mover);
    }
}
