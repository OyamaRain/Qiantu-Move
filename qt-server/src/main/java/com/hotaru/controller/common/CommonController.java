package com.hotaru.controller.common;

import com.hotaru.result.Result;
import com.hotaru.utils.AliOssUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/common/upload")
    @Tag(name = "通用接口")
    @Operation(summary = "文件上传")
    public Result<String> upload(@RequestParam("file")MultipartFile file) {
        log.info("文件上传：{}", file.getOriginalFilename());

        try {
            // 1. 获取原始文件名并截取后缀
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            
            // 2. 构造唯一文件名 (UUID)
            String objectName = UUID.randomUUID().toString() + extension;

            // 3. 上传到OSS
            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
            
            return Result.success(filePath);
        } catch (IOException e) {
            log.error("文件上传失败：{}", e.getMessage());
        }
        return Result.error("文件上传失败");
    }
}