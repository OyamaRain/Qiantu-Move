package com.hotaru.service.mover.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotaru.constant.MessageConstant;
import com.hotaru.context.BaseContext;
import com.hotaru.dto.mover.CommentPageQueryDTO;
import com.hotaru.entity.Mover;
import com.hotaru.entity.MoverComment;
import com.hotaru.enumeration.RoleEnum;
import com.hotaru.exception.CommentNotFoundException;
import com.hotaru.exception.MoverNotFoundException;
import com.hotaru.mapper.MoverCommentMapper;
import com.hotaru.mapper.MoverMapper;
import com.hotaru.mapper.OrderMapper;
import com.hotaru.result.PageResult;
import com.hotaru.service.mover.MoverInfoService;
import com.hotaru.vo.mover.MoverCommentVO;
import com.hotaru.vo.mover.MoverInfoOrdersVO;
import com.hotaru.vo.mover.MoverInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MoverInfoServiceImpl implements MoverInfoService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private MoverMapper moverMapper;
    @Autowired
    private MoverCommentMapper moverCommentMapper;

    @Override
    public MoverInfoOrdersVO getMoverInfoOrders(Long currentId) {
        Map<String,Object> orderCount = orderMapper.getOrderCountById(currentId);

        Integer toBeServed = 0;
        Integer completed = 0;

        if (orderCount != null) {
            if (orderCount.get("toBeServed") != null) {
                toBeServed = ((Number) orderCount.get("toBeServed")).intValue();
            }
            if (orderCount.get("completed") != null) {
                completed = ((Number) orderCount.get("completed")).intValue();
            }
        }

        return MoverInfoOrdersVO.builder()
                .toBeServed(toBeServed)
                .completed(completed)
                .build();
    }

    @Override
    public MoverInfoVO getMoverInfo(Long currentId) {
        Mover currentMover = moverMapper.getById(currentId);

        if (currentMover == null) {
            throw new MoverNotFoundException(MessageConstant.MOVER_NOT_FOUND);
        }

        MoverInfoVO vo = new MoverInfoVO();
        vo.setRole(String.valueOf(RoleEnum.MOVER));
        vo.setName(currentMover.getName());
        vo.setAvatar(currentMover.getAvatar());
        vo.setPhone(currentMover.getPhone());
        return vo;
    }

    @Override
    public PageResult getComments(CommentPageQueryDTO commentPageQueryDTO) {
        // 1. 设置当前师傅 ID 并开启分页
        commentPageQueryDTO.setMoverId(BaseContext.getCurrentId());
        PageHelper.startPage(commentPageQueryDTO.getPage(), commentPageQueryDTO.getPageSize());

        // 2. 从数据库获取原始数据
        Page<MoverComment> page = moverCommentMapper.getByMoverId(commentPageQueryDTO);

        // 3. 对结果集进行脱敏/替换处理
        List<MoverCommentVO> voList = page.getResult().stream().map(comment -> {
            MoverCommentVO vo = new MoverCommentVO();
            BeanUtils.copyProperties(comment, vo); // 复制属性

            // 逻辑判断：3分及以下替换内容
            if (comment.getScore() != null && comment.getScore() <= 3) {
                vo.setContent("系统提示：服务有待改进");
            }

            // 对用户名进行隐藏
            vo.setUserName("迁途用户");

            return vo;
        }).collect(Collectors.toList());

        return new PageResult(page.getTotal(), voList);

    }
}
