package com.hotaru.constant;

/**
 * 订单状态常量
 */
public class OrderStatusConstant {

    //未支付
    public static final Integer PENDING_PAY = 0;

    //已支付
    public static final Integer PAID = 1;

    //待接单
    public static final Integer PENDING_ASSIGN = 2;

    //已接单
    public static final Integer ASSIGNED = 3;

    //已完成
    public static final Integer COMPLETED = 4;

    //已取消
    public static final Integer CANCELLED = 5;

}
