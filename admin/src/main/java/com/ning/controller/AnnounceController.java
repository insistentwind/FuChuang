package com.ning.controller;

import com.ning.domain.dto.AnnouncePublishDto;
import com.ning.domain.entity.Announce;
import com.ning.domain.result.PageResult;
import com.ning.domain.result.Result;
import com.ning.domain.vo.AnnouncePageVo;
import com.ning.domain.vo.AnnounceVo;
import com.ning.mapper.AnnounceMapper;
import com.ning.service.AnnounceService;
import com.ning.service.impl.AnnounceServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: qjn
 * @create: 2024/03/31 17:47
 **/
@RestController
@Slf4j
@Api(tags = "系统公告")
@RequestMapping("/system/announce")
public class AnnounceController {

    @Autowired
    private AnnounceService announceService;
    /**
     * 发布系统公告
     * @return
     */
    @ApiOperation("发布系统公告")
    @PostMapping
    public Result<String> publishAnnounce(@RequestBody AnnouncePublishDto announcePublishDto){
        return announceService.add(announcePublishDto);
    }

    /**
     * 查看公告列表
     * @return
     */
    @ApiOperation("查看公告列表")
    @GetMapping("/list")
    public Result<PageResult> page(AnnouncePageVo announcePageVo){
        log.info("查询文章列表：{}",announcePageVo);
        return announceService.getPage(announcePageVo);
    }


    /**
     * 公告内容回显
     * @return
     */
    @ApiOperation("公告内容回显")
    @GetMapping("/{id}")
    public Result<AnnounceVo> selectAnnounceById(@PathVariable Integer id){
        log.info("数据回显，查询对应文章详情");
        return announceService.selectAnnounceById(id);
    }
    /**
     * 更新公告
     */
    @ApiOperation("更新公告")
    @PutMapping
    public Result<String> updateAnnounceById(@RequestBody AnnouncePublishDto AnnouncePublishDto){
        log.info("更新对应的公告");
        return announceService.updateByEntity(AnnouncePublishDto);
    }

    /**
     * 逻辑删除公告
     * @param ids
     * @return
     */
    @ApiOperation("逻辑删除公告")
    @DeleteMapping("/{ids}")
    public Result<String> deleteAnnounceByIds(@PathVariable List<Integer> ids){
        log.info("逻辑删除文章:{}",ids);
        return announceService.deleteById(ids);
    }
}