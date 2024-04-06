package com.ning.controller;

import com.ning.annotation.SecurityParameter;
import com.ning.constants.MessageConstant;
import com.ning.constants.SystemConstants;
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
import java.util.UUID;

/**
 * @Author: qjn
 * @Date: 2024/1/9 0:23
 */
@RestController
@Slf4j
@Api(tags = "文件上传接口")
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/resume")
    @ApiOperation("简历上传接口")
    //开启异步后，由于multipartfile文件是临时文件，在接口return时会被销毁，因此开启异步有可能会找不到文件
    public Result<String> uploadResume(@RequestParam MultipartFile file){
        log.info("简历上传:{}",file);
        if(file == null){
            throw new BaseException("发生错误,文件上传失败,请重试");
        }
        String originalFilename = file.getOriginalFilename();
        if(!originalFilename.endsWith(".pdf") && !originalFilename.endsWith(".word")){
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
            log.error("文件上传失败: {}",e);
        }

        return Result.error(MessageConstant.UPLOAD_FAILED);
    }

    /**
     * 头像上传接口
     * @param file
     * @return
     */
    @PostMapping("/avatar")
    @ApiOperation("头像上传接口")
    //开启异步后，由于multipartfile文件是临时文件，在接口return时会被销毁，因此开启异步有可能会找不到文件
    public Result<String> uploadAvatar(@RequestParam MultipartFile file){
        log.info("头像上传:{}",file);
        if(file == null){
            throw new BaseException("发生错误,文件上传失败,请重试");
        }
        String originalFilename = file.getOriginalFilename();
        if(!originalFilename.endsWith(".png") && !originalFilename.endsWith(".jpg")){
            //文件类型错误
            throw new RuntimeException("头像文件类型错误");
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
            log.error("头像上传失败: {}",e);
        }

        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
