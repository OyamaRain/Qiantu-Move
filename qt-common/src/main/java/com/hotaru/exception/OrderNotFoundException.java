package com.hotaru.exception;

// 订单未找到异常
public class OrderNotFoundException extends BaseException {

    public OrderNotFoundException() {
    }

    public OrderNotFoundException(String msg) {
        super(msg);
    }

}
