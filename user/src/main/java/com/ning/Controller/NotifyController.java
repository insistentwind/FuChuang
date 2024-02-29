package com.ning.Controller;

import com.ning.domain.dto.NotifyDto;
import com.ning.domain.result.Result;
import com.ning.domain.systemConstants.SystemConstants;
import com.ning.service.NotifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: qjn
 * @create: 2024/02/24 19:38
 **/
@RestController
@RequestMapping("/notify")
@Slf4j
@Api(tags = "用户收到的消息")
public class NotifyController {
    @Autowired
    private NotifyService notifyService;

    /**
     * 新增消息通知
     * @param notifyDto
     * @return
     */
    @PostMapping("")
    @ApiOperation("新增消息通知")
    public Result<String> create(@RequestBody NotifyDto notifyDto) {
        return notifyService.create(notifyDto);
    }

    /**
     * 根据用户名修改消息状态为已读
     * @param notifyDto
     * @return
     */
    @PutMapping("")
    @ApiOperation("根据用户名修改消息状态为已读")
    public Result<String> update(@RequestBody NotifyDto notifyDto) {
        return notifyService.updateByUsername(notifyDto);
    }

    /**
     * 根据用户名和状态码查询该用户收到的所有通知
     * @param userId
     * @param isRead
     * @return
     */
    @GetMapping()
    @ApiOperation("根据用户名和状态码查询该用户收到的所有通知")
    public Result<List<NotifyDto>> getList(@RequestParam Integer userId, @RequestParam Integer isRead) {
        return notifyService.getList(userId,isRead);
    }

    /**
     * 根据用户名查询该用户收到的所有通知
     * @param userId
     * @return
     */
    @GetMapping("/all")
    @ApiOperation("根据用户名查询该用户收到的所有通知")
    public Result<List<NotifyDto>> getAll(@RequestParam Integer userId) {
        return notifyService.getList(userId, SystemConstants.NOTIFY_STATUS);
    }

}