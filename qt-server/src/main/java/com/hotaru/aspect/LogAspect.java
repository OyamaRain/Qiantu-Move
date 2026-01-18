package com.hotaru.aspect;

import com.hotaru.annotation.Log;
import com.hotaru.context.BaseContext;
import com.hotaru.entity.SysLog;
import com.hotaru.mapper.LogMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect // 切面
@Component // 交给Spring管理
@Slf4j
public class LogAspect {

    @Autowired
    private LogMapper logMapper;

    // 只拦截Log注解的方法
    @Pointcut("@annotation(com.hotaru.annotation.Log)")
    public void logPointCut() {}

    // 日志记录，在添加了注解的方法成功执行后执行
    @AfterReturning(pointcut = "logPointCut()")
    public void recordLog(JoinPoint joinPoint) {

        // 获取方法签名 --- 方法签名包含了方法的所有信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // 获取方法上的Log注解
        Log logAnnotation = method.getAnnotation(Log.class);

        // 封装日志对象
        SysLog sysLog = new SysLog();
        sysLog.setEmployeeId(BaseContext.getCurrentId());
        sysLog.setEmployeeName(BaseContext.getCurrentName());
        sysLog.setOperation(logAnnotation.value());
        sysLog.setMethod(signature.getDeclaringTypeName() + "." + method.getName());
        sysLog.setCreateTime(LocalDateTime.now());

        log.info("【操作日志】{}", logAnnotation.value());

        // 存入数据库
        logMapper.insert(sysLog);
    }
}
