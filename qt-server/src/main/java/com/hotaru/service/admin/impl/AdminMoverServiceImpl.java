package com.hotaru.service.admin.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotaru.dto.admin.MoverApplyPageQueryDTO;
import com.hotaru.dto.admin.MoverApplyRejectReasonDTO;
import com.hotaru.dto.admin.MoverDTO;
import com.hotaru.dto.admin.MoverPageQueryDTO;
import com.hotaru.entity.Mover;
import com.hotaru.entity.MoverApply;
import com.hotaru.enumeration.RoleEnum;
import com.hotaru.exception.MoverApplyException;
import com.hotaru.mapper.ApplyMapper;
import com.hotaru.mapper.MoverMapper;
import com.hotaru.mapper.UserMapper;
import com.hotaru.result.PageResult;
import com.hotaru.result.Result;
import com.hotaru.service.admin.AdminMoverService;
import com.hotaru.vo.admin.MoverVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminMoverServiceImpl implements AdminMoverService {

    @Autowired
    private MoverMapper moverMapper;
    @Autowired
    private ApplyMapper applyMapper;
    @Autowired
    private UserMapper userMapper;

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

    @Override
    public PageResult getApplyPage(MoverApplyPageQueryDTO moverApplyPageQueryDTO) {
        PageHelper.startPage(moverApplyPageQueryDTO.getPage(), moverApplyPageQueryDTO.getPageSize());

        Page<MoverApply> page = applyMapper.pageQuery(moverApplyPageQueryDTO);
        long total = page.getTotal();
        List<MoverApply> result = page.getResult();

        return new PageResult(total, result);
    }

    @Override
    @Transactional
    public void approveApply(Long id) {
        MoverApply apply = applyMapper.selectByUserId(id);
        if (apply == null || apply.getApplyStatus() != 0) {
            throw new MoverApplyException("申请状态异常");
        }

        // 1. 更新申请状态
        applyMapper.updateStatus(apply.getId(), 1, null);

        // 2. 更新用户角色
        userMapper.updateRole(apply.getUserId(), RoleEnum.MOVER);

        // 3. 初始化师傅信息
        moverMapper.insert(Mover.builder()
                .name(apply.getName())
                .phone(apply.getPhone())
                .avatar(null)
                .createTime(LocalDateTime.now())
                .rating(5.0)
                .orderCount(0)
                .status(1)
                .build());
    }

    @Override
    public void rejectApply(Long id, MoverApplyRejectReasonDTO moverApplyRejectReasonDTO) {
        MoverApply apply = applyMapper.selectByUserId(id);
        if (apply == null || apply.getApplyStatus() != 0) {
            throw new MoverApplyException("申请状态异常");
        }

        // 更新申请状态
        applyMapper.updateStatus(apply.getId(), 2, moverApplyRejectReasonDTO.getRejectReason());

    }
}
