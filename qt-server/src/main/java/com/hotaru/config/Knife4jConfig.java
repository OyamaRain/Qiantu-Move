package com.hotaru.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 配置Knife4j的相关信息
@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("项目API文档")
                        .version("1.0")
                        .description("迁途搬家项目API文档")
                        .contact(new Contact().name("Hotaru")));
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                // 分组名称
                .group("管理端接口")
                // 只扫描管理端的路径
                .pathsToMatch("/admin/**")
                .packagesToScan("com.hotaru.controller.admin")
                .build();
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("用户端接口")
                .pathsToMatch("/user/**")
                .packagesToScan("com.hotaru.controller.user")
                .build();
    }

    @Bean
    public GroupedOpenApi moverApi() {
        return GroupedOpenApi.builder()
                .group("师傅端接口")
                .pathsToMatch("/mover/**")
                .packagesToScan("com.hotaru.controller.mover")
                .build();
    }

}