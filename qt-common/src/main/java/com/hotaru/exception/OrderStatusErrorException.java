package com.hotaru.exception;

// 订单状态异常
public class OrderStatusErrorException extends BaseException {

    public OrderStatusErrorException() {
    }

    public OrderStatusErrorException(String msg) {
        super(msg);
    }

}
