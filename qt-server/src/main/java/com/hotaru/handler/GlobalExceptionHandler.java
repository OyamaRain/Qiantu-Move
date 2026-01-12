package com.hotaru.handler;

import com.hotaru.constant.MessageConstant;
import com.hotaru.exception.BaseException;
import com.hotaru.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

import static com.hotaru.constant.MessageConstant.ALREADY_EXISTS;

//全局异常处理器
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //业务异常
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    //SQL语句异常
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        String message = ex.getMessage();
        if (message.contains("Duplicate entry")) {
            String[] s = message.split(" ");
            String msg = s[2] + ALREADY_EXISTS;
            return Result.error(msg);
        } else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }

}
