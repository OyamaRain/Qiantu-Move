package com.hotaru.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
@Slf4j
public class DataSourceWarmUpRunner implements ApplicationRunner {

    private final DataSource dataSource;

    public DataSourceWarmUpRunner(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(ApplicationArguments args) {
        try (Connection connection = dataSource.getConnection()) {
            log.info("✅ 数据库连接预热成功");
        } catch (Exception e) {
            log.error("❌ 数据库连接预热失败：" + e.getMessage());
        }
    }
}
