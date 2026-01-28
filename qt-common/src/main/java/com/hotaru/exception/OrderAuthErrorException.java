package com.hotaru.exception;

// 订单归属错误异常
public class OrderAuthErrorException extends BaseException {

    public OrderAuthErrorException() {
    }

    public OrderAuthErrorException(String msg) {
        super(msg);
    }

}
