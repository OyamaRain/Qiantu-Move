package com.hotaru.exception;

// 评价未找到异常
public class CommentNotFoundException extends BaseException {

    public CommentNotFoundException() {
    }

    public CommentNotFoundException(String msg) {
        super(msg);
    }

}
