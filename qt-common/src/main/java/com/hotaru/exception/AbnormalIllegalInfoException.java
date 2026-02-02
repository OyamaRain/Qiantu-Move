package com.hotaru.exception;

// 非法获取用户信息异常
public class AbnormalIllegalInfoException extends BaseException {

    public AbnormalIllegalInfoException() {
    }

    public AbnormalIllegalInfoException(String msg) {
        super(msg);
    }

}
