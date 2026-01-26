package com.hotaru;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.hotaru"}) // 扫描子包下的所有组件
@EnableTransactionManagement
@Slf4j
public class QtServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(QtServerApplication.class, args);
        log.info("✅ 服务器已启动");
    }

}
