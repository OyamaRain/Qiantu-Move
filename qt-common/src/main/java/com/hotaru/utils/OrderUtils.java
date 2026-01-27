package com.hotaru.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class OrderUtils {

    /**
     * 生成订单号：QT + yyyyMMddHHmmss + 6位随机数
     */
    public static String generateOrderSn() {
        // 1. 时间戳部分：精确到秒（或毫秒）
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        
        // 2. 随机数部分：生成 100000 到 999999 之间的随机数
        int randomNum = ThreadLocalRandom.current().nextInt(100000, 1000000);
        
        // 3. 拼接
        return "QT" + timestamp + randomNum;
    }
}