package com.hotaru.service.user;

import com.hotaru.dto.user.CommentPageQueryDTO;
import com.hotaru.dto.user.MoverApplyDTO;
import com.hotaru.result.PageResult;
import com.hotaru.vo.user.CommentDetailVO;
import com.hotaru.vo.user.UserInfoVO;


public interface UserInfoService {
    //获取用户信息
    UserInfoVO getUserInfo(Long currentId);

    //获取用户评论
    PageResult getUserComments(CommentPageQueryDTO commentPageQueryDTO);

    //获取用户评论详情
    CommentDetailVO getCommentDetail(Long id);

    //用户申请成为搬家师傅
    void applyMover(MoverApplyDTO moverApplyDTO);
}
