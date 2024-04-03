package com.ning.Mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: qjn
 * @create: 2024/04/02 22:40
 **/
@Component
@Slf4j
public class Publisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;

}