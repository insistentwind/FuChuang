package com.ning.Listener;

import com.ning.constants.MqConstants;
import com.ning.constants.SystemConstants;
import com.ning.domain.dto.NotifyDto;
import com.ning.domain.dto.PositionMessage;
import com.ning.domain.entity.UNotify;
import com.ning.mapper.db02.UNotifyMapper;
import com.ning.service.UNotifyService;
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
    private UNotifyService uNotifyService;
    @Autowired
    private UNotifyMapper uNotifyMapper;

    /**
     * 公司职位上新或者删除通知
     */
    @RabbitListener(queues = MqConstants.POSITION_INSERT_QUEUE)
    public void PositionInsertOrUpdate(PositionMessage positionMessage) {
        try {
            //因为设置redis的端口是不开放的，以防被攻击服务器压力倍增
            String message = positionMessage.getMessage();
            //拿到公司信息，在redis中遍历拿到关注用户的信息
            Set<String> set = positionMessage.getSet();
            for (String user : set) {
                NotifyDto notifyDto = new NotifyDto();
                notifyDto.setUserId(Integer.valueOf(user));
                notifyDto.setContent(message);
                UNotify uNotify = BeanCopyUtils.copyBean(notifyDto, UNotify.class);
                uNotify.setTime(LocalDateTime.now())
                        .setId(null)
                        .setIsRead(SystemConstants.HAS_NO_READ);
                uNotifyMapper.insert(uNotify);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}