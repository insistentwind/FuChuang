package com.ning.controller;

import com.ning.constants.MessageConstant;
import com.ning.exception.BaseException;
import com.ning.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ning.domain.result.Result;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/**
 * @Author: qjn
 * @Date: 2024/1/9 0:23
 */
@RestController
@Slf4j
@Api(tags = "文件（头像）上传接口")
@RequestMapping("system/upload")
public class UploadController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping
    @ApiOperation("文件（头像）上传接口")
    public Result<String> upload(@RequestParam MultipartFile file) {
        log.info("文件（头像）上传:{}", file);
        if(file == null){
            throw new BaseException("发生错误,文件上传失败,请重试");
        }
        String originalFilename = file.getOriginalFilename();
        if (!Objects.requireNonNull(originalFilename).endsWith(".img") &&
                !Objects.requireNonNull(originalFilename).endsWith(".jpg") &&
                !Objects.requireNonNull(originalFilename).endsWith(".png") &&
                !Objects.requireNonNull(originalFilename).endsWith(".IMG") &&
                !Objects.requireNonNull(originalFilename).endsWith(".JPG") &&
                !Objects.requireNonNull(originalFilename).endsWith(".PNG")) {
            //文件类型错误
            throw new RuntimeException("文件类型错误");
        }
        try {
            //原始文件名
//          String originalFilename = file.getOriginalFilename();
            //截取原始文件的后缀,从最后一个.处开始截取
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            //构造新的文件名
            String objectName = UUID.randomUUID().toString() + extension;
            //文件的请求路径
            String filePath = aliOssUtil.upload(file.getBytes(), objectName);

            return Result.success(filePath);
        } catch (IOException e) {
            log.error("文件上传失败: {}", e);
        }

        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
