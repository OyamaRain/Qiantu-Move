package com.hotaru.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 表示该注解可以用于方法上
@Retention(RetentionPolicy.RUNTIME) // 表示该注解在运行时保留
public @interface Log {
    // 操作说明
    String value();
}
