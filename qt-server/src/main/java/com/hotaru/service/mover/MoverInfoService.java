package com.hotaru.service.mover;

import com.hotaru.dto.mover.CommentPageQueryDTO;
import com.hotaru.result.PageResult;
import com.hotaru.vo.mover.OngoingOrdersVO;
import com.hotaru.vo.mover.ToBeResponseOrdersVO;
import com.hotaru.vo.mover.MoverInfoVO;

public interface MoverInfoService {
    //获取个人信息
    MoverInfoVO getMoverInfo(Long currentId);

    //获取评价列表
    PageResult getComments(CommentPageQueryDTO commentPageQueryDTO);

    //获取待接单订单数量
    ToBeResponseOrdersVO getToBeResponseOrders(Long currentId);

    //获取进行中订单数量
    OngoingOrdersVO getOngoingOrders(Long currentId);
}
