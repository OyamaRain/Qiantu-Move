package com.hotaru.service.user.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hotaru.constant.MessageConstant;
import com.hotaru.context.BaseContext;
import com.hotaru.dto.user.CommentPageQueryDTO;
import com.hotaru.dto.user.MoverApplyDTO;
import com.hotaru.entity.*;
import com.hotaru.enumeration.RoleEnum;
import com.hotaru.exception.AbnormalIllegalInfoException;
import com.hotaru.exception.CommentNotFoundException;
import com.hotaru.exception.MoverApplyException;
import com.hotaru.exception.UserNotFoundException;
import com.hotaru.mapper.*;
import com.hotaru.result.PageResult;
import com.hotaru.service.user.UserInfoService;
import com.hotaru.vo.user.CommentDetailVO;
import com.hotaru.vo.user.CommentVO;
import com.hotaru.vo.user.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private OrderCommentMapper orderCommentMapper;
    @Autowired
    private MoverCommentMapper moverCommentMapper;
    @Autowired
    private MoverMapper moverMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ApplyMapper applyMapper;

    @Override
    public UserInfoVO getUserInfo(Long currentId) {
        User user = userInfoMapper.getUserInfo(currentId);

        if(user == null){
            throw new UserNotFoundException(MessageConstant.USER_NOT_FOUND);
        }

        if(!currentId.equals(user.getId())){
            throw new AbnormalIllegalInfoException(MessageConstant.ILLEGAL_OPERATION);
        }

        return UserInfoVO.builder()
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .phone(user.getPhone())
                .role(String.valueOf(user.getRole()))
                .build();
    }

    @Override
    public PageResult getUserComments(CommentPageQueryDTO commentPageQueryDTO) {
        // 1. 设置默认值，防止 Page 或 PageSize 为 null 导致崩溃
        int pageNum = (commentPageQueryDTO.getPage() == null) ? 1 : commentPageQueryDTO.getPage();
        int pageSize = (commentPageQueryDTO.getPageSize() == null) ? 10 : commentPageQueryDTO.getPageSize();

        // 2. 开启分页
        PageHelper.startPage(pageNum, pageSize);

        // 3. 执行查询
        Page<CommentVO> page = commentMapper.PageQuery(commentPageQueryDTO);

        // 4. 封装返回
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public CommentDetailVO getCommentDetail(Long id) {
        OrderComment oc = orderCommentMapper.getByOrderId(id);
        MoverComment mc = moverCommentMapper.getByOrderId(id);
        String orderNo = orderMapper.getById(id).getOrderNo();
        Mover currentMover = moverMapper.getById(orderMapper.getById(id).getMoverId());

        if (oc == null && mc == null) {
            throw new CommentNotFoundException(MessageConstant.COMMENT_NOT_FOUND);
        }

        return CommentDetailVO.builder()
                .orderNo(orderNo)
                .orderScore(oc.getScore())
                .orderContent(oc.getContent())
                .moverName(currentMover.getName())
                .moverAvatar(currentMover.getAvatar())
                .moverScore(mc.getScore())
                .moverContent(mc.getContent())
                .createTime(oc.getCreateTime())
                .build();

    }

    @Override
    public void applyMover(MoverApplyDTO moverApplyDTO) {
        // 1. 是否已是师傅
        Long currentId = BaseContext.getCurrentId();
        User user = userMapper.getById(currentId);
        if (RoleEnum.MOVER.equals(user.getRole())) {
            throw new MoverApplyException("您已经是师傅了");
        }

        // 2. 是否已有申请
        MoverApply apply = applyMapper.selectByUserId(currentId);
        if (apply != null) {
            if (apply.getApplyStatus() == 0) {
                throw new MoverApplyException("申请正在审核中");
            }
            if (apply.getApplyStatus() == 1) {
                throw new MoverApplyException("已通过审核");
            }
        }

        // 3. 保存申请
        MoverApply newApply = MoverApply.builder()
                .userId(currentId)
                .name(moverApplyDTO.getName())
                .phone(moverApplyDTO.getPhone())
                .applyStatus(0)
                .createTime(LocalDateTime.now())
                .build();

        applyMapper.insert(newApply);
    }
}
