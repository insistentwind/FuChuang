package com.ning.listener;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ning.constants.MqConstants;
import com.ning.constants.SystemConstants;
import com.ning.domain.entity.UserKey;
import com.ning.service.UserKeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author: qjn
 * @create: 2024/04/02 22:49
 **/
@Component
@Slf4j
public class UserKeyListener {
    @Autowired
    private UserKeyService userKeyService;

    @RabbitListener(queues = MqConstants.FUCHUANG_INSERT_QUEUE)
    public void listenKeyInsertOrUpdate(UserKey userKey){
        try {
            log.info("userKey",userKey);
            //这里每个用户的密钥都唯一
            LambdaQueryWrapper<UserKey> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserKey::getUserId,userKey.getUserId());
            UserKey one = userKeyService.getOne(wrapper);
            //判断如果没有这个userkey就插入
            if (one == null || one.getSecretKey() == null){
                userKeyService.save(userKey);
            }
            else {
                //不操作
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(SystemConstants.UP_TIME);
        }
    }

    /**
     * 监听删除的业务
     * @param userKey
     */
    @RabbitListener(queues = MqConstants.FUCHUANG_DELETE_QUEUE)
    public void listenHotelDelete(UserKey userKey){
        try {
            userKeyService.removeById(userKey);
        } catch (Exception e) {
            throw new RuntimeException("出现未知错误");
        }
    }

}