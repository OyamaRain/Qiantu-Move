package com.hotaru.service.mover;

import com.hotaru.dto.mover.CommentPageQueryDTO;
import com.hotaru.result.PageResult;
import com.hotaru.vo.mover.MoverInfoOrdersVO;
import com.hotaru.vo.mover.MoverInfoVO;

public interface MoverInfoService {
    //获取顶部订单状态
    MoverInfoOrdersVO getMoverInfoOrders(Long currentId);

    //获取个人信息
    MoverInfoVO getMoverInfo(Long currentId);

    //获取评价列表
    PageResult getComments(CommentPageQueryDTO commentPageQueryDTO);
}
