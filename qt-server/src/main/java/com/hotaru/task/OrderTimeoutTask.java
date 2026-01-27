package com.hotaru.task;

import com.hotaru.constant.OrderStatusConstant;
import com.hotaru.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTimeoutTask {

    @Autowired
    private OrderMapper orderMapper;

    //自动处理超时订单
    @Scheduled(cron = "0 * * * * ?")
    @Transactional
    public void cancelOrders() {
        LocalDateTime limit = LocalDateTime.now().minusMinutes(15);
        List<Long> ids = orderMapper.selectTimeoutIds(limit, OrderStatusConstant.PENDING_PAY);
        if (!ids.isEmpty()) {
            log.info("检测到 {} 笔超时订单", ids.size());
            orderMapper.batchCancel(ids, OrderStatusConstant.CANCELLED);
            log.info("⚠️ 系统已自动取消超时订单");
        }
    }

}
