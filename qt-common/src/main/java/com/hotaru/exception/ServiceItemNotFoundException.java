package com.hotaru.exception;

// 服务项异常
public class ServiceItemNotFoundException extends BaseException {

    public ServiceItemNotFoundException() {
    }

    public ServiceItemNotFoundException(String msg) {
        super(msg);
    }

}
