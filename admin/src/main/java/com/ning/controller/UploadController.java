package com.ning.controller;

import com.ning.domain.Result.ResponseResult;
import com.ning.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.TreeMap;

/**
 * @Author: qjn
 * @Date: 2023/11/27 18:16
 */
@RestController("AdminUploadController")
@Slf4j
@Api(tags = "admin文件上传接口")
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public ResponseResult uploadImage(MultipartFile img){
        try {
            return uploadService.uploadImg(img);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("文件上传失败");
        }
    }
}
