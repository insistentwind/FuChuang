package com.ning.config;

import com.ning.constants.MqConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: qjn
 * @create: 2024/04/02 20:31
 **/
@Slf4j
@Configuration
@EnableRabbit
public class MqConfirmConfig implements ApplicationContextAware {

    /**
     * 消息转换器
     * @return
     */
    @Bean
    public MessageConverter jacksonMessageConvertor(){
        return new Jackson2JsonMessageConverter();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);
        // 配置回调
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returned) {
                log.debug("收到消息的return callback，exchange:{}, key:{}, msg:{}, code:{}, text:{}",
                        returned.getExchange(), returned.getRoutingKey(), returned.getMessage(),
                        returned.getReplyCode(), returned.getReplyText());
            }
        });
    }
    /**
     * 定义Topic交换机
     * @return
     */
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(MqConstants.FUCHUANG_EXCHANGE,true,false);
    }
    @Bean
    public TopicExchange positionTopicExchange(){
        return new TopicExchange(MqConstants.POSITION_EXCHANGE,true,false);
    }


    /**
     * 新增队列和修改队列
     * @return
     */
    @Bean
    public Queue insertQueue(){
        return new Queue(MqConstants.FUCHUANG_INSERT_QUEUE,true);
    }

    @Bean
    public Queue positionInsertQueue(){
        return new Queue(MqConstants.POSITION_INSERT_QUEUE,true);
    }


    /**
     * 删除队列
     * @return
     */
    @Bean
    public Queue deleteQueue(){
        return new Queue(MqConstants.FUCHUANG_DELETE_QUEUE,true);
    }
    @Bean
    public Queue positionDeleteQueue(){
        return new Queue(MqConstants.POSITION_DELETE_QUEUE,true);
    }
//
    /**
     * 队列交换机绑定
     * @return
     */
    @Bean
    public Binding insertQueueBinding(){
        return BindingBuilder.bind(insertQueue()).
                to(topicExchange()).with(MqConstants.FUCHUANG_INSERT_KEY);
    }
    @Bean
    public Binding positionInsertQueueBinding(){
        return BindingBuilder.bind(positionInsertQueue()).
                to(positionTopicExchange()).with(MqConstants.FUCHUANG_INSERT_KEY);
    }
//
//
    /**
     * 队列交换机绑定
     * @return
     */
    @Bean
    public Binding deleteQueueBinding(){
        return BindingBuilder.bind(deleteQueue()).
                to(topicExchange()).with(MqConstants.FUCHUANG_DELETE_KEY);
    }
    @Bean
    public Binding positionDeleteQueueBinding(){
        return BindingBuilder.bind(positionDeleteQueue()).
                to(positionTopicExchange()).with(MqConstants.FUCHUANG_DELETE_KEY);
    }

}