package com.hotaru.exception;

// 订单支付状态错误异常
public class OrderPayStatusErrorException extends BaseException {

    public OrderPayStatusErrorException() {
    }

    public OrderPayStatusErrorException(String msg) {
        super(msg);
    }

}
