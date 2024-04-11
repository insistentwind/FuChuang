package com.ning.Listener;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ning.constants.MqConstants;
import com.ning.constants.SystemConstants;
import com.ning.domain.dto.NotifyDto;
import com.ning.domain.dto.PositionMessage;
import com.ning.domain.entity.Notify;
import com.ning.entity.UserKey;
import com.ning.exception.BaseException;
import com.ning.service.CompanyService;
import com.ning.service.NotifyService;
import com.ning.utils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author: qjn
 * @create: 2024/04/10 20:44
 **/
@Component
@Slf4j
public class PositionListener {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private NotifyService notifyService;

    /**
     * 公司职位上新或者删除通知
     */
    @RabbitListener(queues = MqConstants.POSITION_INSERT_QUEUE)
    public void PositionInsertOrUpdate(PositionMessage positionMessage) {
        try {
            String companyName = positionMessage.getCompanyName();
            String message = positionMessage.getMessage();
            //拿到公司信息，在redis中遍历拿到关注用户的信息
            Set<String> set = redisTemplate.opsForHash().keys(companyName);
            for (String user : set) {
                NotifyDto notifyDto = new NotifyDto();
                notifyDto.setUserId(Integer.valueOf(user));
                notifyDto.setContent(message);
                Notify notify = BeanCopyUtils.copyBean(notifyDto, Notify.class);
                notify.setTime(LocalDateTime.now());
                notifyService.save(notify);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}