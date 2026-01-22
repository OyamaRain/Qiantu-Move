package com.hotaru.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "qt.aliyun.oss")
@Data
public class AliOssProperties {

    // 阿里云 OSS 配置信息
    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;

}