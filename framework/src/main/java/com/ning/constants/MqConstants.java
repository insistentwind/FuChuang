package com.ning.constants;

public class MqConstants {

    //todo 需要反馈结果就需要使用AmqpTemplate中convertSendAndReceive
    /**
     * 交换机
     */
    public final static String FUCHUANG_EXCHANGE = "fuchuang.topic";



    /**
     * 监听新增和修改的队列
     */
    public final static String FUCHUANG_INSERT_QUEUE = "fuchuang.insert.queue";

    /**
     * 监听查询的队列
     */
    public final static String FUCHUANG_SELECT_QUEUE = "fuchuang.select.queue";

    /**
     * 监听删除的队列
     */
    public final static String FUCHUANG_DELETE_QUEUE = "fuchuang.delete.queue";

    /**
     * 新增或修改的RoutingKey
     */
    public final static String FUCHUANG_INSERT_KEY = "fuchuang.insert";
    /**
     * 删除的RoutingKey
     */
    public final static String FUCHUANG_DELETE_KEY = "fuchuang.delete";

    /**
     * 查询的RoutingKey
     */
    public final static String FUCHUANG_SELECT_KEY = "fuchuang.select";



    public final static String POSITION_EXCHANGE = "position.topic";
    /**
     * 监听新增和修改的队列
     */
    public final static String POSITION_INSERT_QUEUE = "position.insert.queue";

    /**
     * 监听删除的队列
     */
    public final static String POSITION_DELETE_QUEUE = "position.delete.queue";

}
