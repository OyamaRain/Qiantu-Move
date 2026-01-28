package com.hotaru.exception;

// 订单对应的师傅未找到异常
public class MoverNotFoundException extends BaseException {

    public MoverNotFoundException() {
    }

    public MoverNotFoundException(String msg) {
        super(msg);
    }

}
