package com.hotaru.exception;

// 用户未找到异常
public class UserNotFoundException extends BaseException {

    public UserNotFoundException() {
    }

    public UserNotFoundException(String msg) {
        super(msg);
    }

}
