package com.ning.Listener;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.Insert;
import com.ning.constants.MqConstants;
import com.ning.constants.SystemConstants;
import com.ning.entity.UserKey;
import com.ning.exception.BaseException;
import com.ning.service.UserKeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
            System.out.println(userKey);
            //这里每个用户的密钥都唯一
            LambdaQueryWrapper<UserKey> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(UserKey::getUserId,userKey.getUserId());
            UserKey one = userKeyService.getOne(wrapper);
            if (one == null || one.getSecretKey() == null){
                userKeyService.save(userKey);
            }
            else {
//                one.setSecretKey(userKey.getSecretKey());
//                userKeyService.updateById(one);
            }
        }
        catch (Exception e){
            e.printStackTrace();
//            throw new BaseException(SystemConstants.UP_TIME);
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