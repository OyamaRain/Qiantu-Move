package com.hotaru.service.user;

import com.hotaru.dto.user.CommentPageQueryDTO;
import com.hotaru.result.PageResult;
import com.hotaru.vo.user.CommentDetailVO;
import com.hotaru.vo.user.CommentVO;
import com.hotaru.vo.user.UserInfoOrdersVO;
import com.hotaru.vo.user.UserInfoVO;


public interface UserInfoService {
    //获取用户信息
    UserInfoVO getUserInfo(Long currentId);

    //获取用户订单状态
    UserInfoOrdersVO getUserInfoOrders(Long currentId);

    //获取用户评论
    PageResult getUserComments(CommentPageQueryDTO commentPageQueryDTO);

    //获取用户评论详情
    CommentDetailVO getCommentDetail(Long id);
}
