package com.accompany.controller;

import com.accompany.base.Result;
import com.accompany.util.AliyunOSSUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/upload")
public class FileUploadController {

    @Autowired
    private AliyunOSSUploadUtil aliyunOSSUploadUtil;

    @PostMapping("/image")
    public Result upload(MultipartFile file) throws Exception {
        log.info("文件上传: {}", file.getOriginalFilename());
        //将文件交给OSS存储管理
        String url = aliyunOSSUploadUtil.upload(file.getBytes(), file.getOriginalFilename());
        log.info("文件上传OSS, url: {}", url);

        return Result.success(url);
    }
}
