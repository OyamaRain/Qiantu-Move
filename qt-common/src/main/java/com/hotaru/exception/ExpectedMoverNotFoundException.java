package com.hotaru.exception;

// 期望指派的搬家师傅未找到异常
public class ExpectedMoverNotFoundException extends BaseException {

    public ExpectedMoverNotFoundException() {
    }

    public ExpectedMoverNotFoundException(String msg) {
        super(msg);
    }

}
