package com.ning.websocket;

import com.alibaba.fastjson.JSONObject;
import com.ning.domain.entity.Chat;
import com.ning.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author: qjn
 * @create: 2024/02/25 22:16
 **/
@Controller
@Slf4j
@ServerEndpoint("/ws/message/{uID}")
public class WebSocketServerEndpoint {
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    // 存储当前连接的用户id
    private Integer uID;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    //虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
    //  注：底下WebSocket是当前类名
    private static CopyOnWriteArraySet<WebSocketServerEndpoint> webSockets = new CopyOnWriteArraySet<>();
    // CopyOnWriteArraySet 是 Java 中的一个线程安全的集合类，它提供了一种在并发环境下安全地访问集合元素的方式。
    // 用来存在线连接数
    private static Map<String, Session> sessionPool = new ConcurrentHashMap<>();
    Integer  ContactID = -1;

    private static ChatService chatService;


//    // 这里初始化messageHandler集合
//
//    /**
//     * 消息类型与 MessageHandler 的映射
//     *
//     * 注意，这里设置成静态变量。虽然说 WebsocketServerEndpoint 是单例，但是 Spring Boot 还是会为每个 WebSocket 创建一个 WebsocketServerEndpoint Bean 。
//     */
//    private static final Map<String, MessageHandler> HANDLERS = new HashMap<>();
//
//    @Autowired
//    private ApplicationContext applicationContext;
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        // 通过 ApplicationContext 获得所有 MessageHandler Bean
//        applicationContext.getBeansOfType(MessageHandler.class).values() // 获得所有 MessageHandler Bean
//                .forEach(messageHandler -> HANDLERS.put(messageHandler.getType(), messageHandler)); // 添加到 handlers 中
//        log.info("[afterPropertiesSet][消息处理器数量：{}]", HANDLERS.size());
//    }




    @Resource
    public void setChatService(ChatService chatService){
        WebSocketServerEndpoint.chatService = chatService;
    }

    /**
     * 客户端链接成功调用的方法
     * @param session
     * @param userId
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "uID") String userId){
        this.session = session;//保留session
        if(userId.equals("undefined")){
            log.error("【websocket消息】连接失败,userIdw为空("+userId+")");
            throw new RuntimeException("【websocket消息】连接失败,userIdw为空("+userId+")");
        }
        this.uID = Integer.valueOf(userId);
        // 保存当前的用户id
        if(sessionPool.get(userId) == null){
            //判断如果链接池中不包含此用户，说明是首次连接
            webSockets.add(this);
            // 持久化此聊天
        }
        sessionPool.put(userId,session);
        log.info("【websocket消息】有新的连接，总数为:" + webSockets.size());
    }

    /**
     * 链接断开时
     */
    @OnClose
    public void onClose(){
        try {
            // 关闭当前的聊天线程
            webSockets.remove(this);
            // 根据当前对象的uid关闭对应的聊天线程
            sessionPool.remove(String.valueOf(this.uID));
            log.info("【websocket消息】用户"+this.uID+"连接断开，剩余在线数为:" + webSockets.size());
        }
        catch (Exception e){
            throw new RuntimeException("【websocket消息】用户"+this.uID+"连接断开，剩余在线数为:" + webSockets.size());
        }
    }

    /**
     * 发送错误时的处理
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误,原因:" + error.getMessage());
        session.getAsyncRemote().sendText(error.getMessage());
    }

    /**
     * 当前服务端收到消息时的处理
     * @param message
     */
    @OnMessage
    public void onMessage(String message){
        //解析聊天数据
        JSONObject content = JSONObject.parseObject(message);

        switch (content.getString("type")){
            case "handToken":
                // 第一次聊天，数据库中没有此次消息
                ws_handler_request(content);
                break;
            case "token":
                // 如果传入的类型是认证头 ,那么将当前用户所有的聊天对象返回给目标
                ws_handler_token(content);
                break;
            case "group":
                // 广播消息
                sendAllMessage(content.getString("content"));
                break;
            case "alone":
                // 私聊消息
                ws_message_alone(content);
                break;
//  TODO          case "state":
//                //  修改消息状态
//                chatService.changeChatState(content.getString("sendID"),uID);
//                break;

        }
    }




    /**
     * 消息结构
     * content - info -- recvId (chat 表字段)
     *                -- sendId :sender_id
     *                -- time :timestamp
     *                -- self :selfInfo
     *                -- content
     *                -- target
     *        - recvID      ...接收者id
     *        - type        ...消息的类型
     *        - content     ...消息内容
     *        - readList    ...获取所有未读的消息列表
     *        - _id         ...待定
     *        - type
     *        - time        ...时间
     *        - target      ...对象
     *        - header      ...认证头
     *        - sendID      ...发送者id
     */



    /**
     * 第一次聊天，数据库中没有此用户消息
     * @param content
     */
    private void ws_handler_request(JSONObject content) {
        String recvId = content.getJSONObject("info").getString("recvId");
        // 记录当前准备接收者的id
        Integer ContactID = Integer.valueOf(recvId);
        // 如果数据库中保留有此次会话，那么return
        if(chatService.getChatExistById(uID,ContactID))
            //说明已经发生过对话了
            return;
        Chat chat = new Chat();
        chat.setSenderId(uID);
        chat.setRecvId(Integer.valueOf(recvId));
        chat.setTimestamp(LocalDateTime.now());
        chat.setSelfInfo(content.getJSONObject("info").getString("self"));
        //接收者的信息
        chat.setTarget(content.getJSONObject("info").getString("target"));
        chat.setContent("您好，我非常喜欢贵公司，有信心能够胜任这个职位，期待您的回复。");

        chatService.save(chat);

        // 如果对方hr在线,那么将本次的通知推送给对方
        if(sessionPool.get(recvId)!=null){
            content.put("self",content.getJSONObject("info").getString("self"));
            content.put("target",content.getJSONObject("info").getString("target"));
            content.put("time",LocalDateTime.now());
            content.put("type","alone");
            content.put("content","您收到一位求职者的新消息");
            content.put("recvID",recvId);
            sendOneMessage(content);
        }

    }

    /**
     * 处理认证，返回所有的消息
     * @param content
     */
    private void ws_handler_token(JSONObject content) {

        content.put("header","history认证");
//        content.put("target",uID);//对方的信息
//        content.put("ContactID",this.ContactID);
        // 认证时,将当前用户所有的聊天数据返回至前端
        content.put("content",chatService.getChatById(uID));
        //包含了发送和接收的所有消息
        content.put("recvID",uID);
        //todo 获取所有未读的消息列表
        content.put("readList",chatService.getChatState(uID));

        sendOneMessage(content);
    }

    /**
     * 私聊消息
     * @param content
     */
    private void ws_message_alone(JSONObject content) {
        // 将信息反馈到用户,-1是机器人,不用修改数据库
//        if(content.getJSONObject("target").getInteger("id")==-1) {
//            content.put("recvID", uID);
//            content.put("type", "system");
//            content.put("content", content.getString("content"));
//        }
//        else{
        Chat chat = new Chat();
        chat.setContent(content.getString("content"));
        chat.setSenderId(uID);
        chat.setRecvId(Integer.valueOf(content.getString("recvID")));
        chat.setSelfInfo(content.getString("self"));
        chat.setTarget(content.getString("target"));
        chat.setTimestamp(LocalDateTime.now());
        chatService.save(chat);
        // 修改消息已读状态
//            chatService.changeChatState(uID,content.getString("recvID"),false);
//        }
        sendOneMessage(content);
    }

    /**
     * 广播消息
     * @param message
     */
    private void sendAllMessage(String message) {
        log.info("【websocket消息】广播消息:" + message);
        for (WebSocketServerEndpoint webSocket : webSockets) {
            try {
                if (webSocket.session.isOpen()) {
                    webSocket.session.getAsyncRemote().sendText(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
    }


    /**
     * 发送消息具体代码
     * @param data
     */
    public void sendOneMessage(JSONObject data) {
        // 获取消息发送的对象
        Session session = sessionPool.get(data.getString("recvID"));
        // 会话不为空，且会话为打开的状态
        if (session != null && session.isOpen()) {
            try {
                // token类型执行这段代码
                if (data.getString("type").equals("token")){
                    String uuid = String.valueOf(UUID.randomUUID()).substring(0,8);
                    data.put("target",uuid);
                    data.put("_id",uuid);
                }
//                log.info("【websocket消息】 单点消息:" + data.getString("content"));
//                session.getAsyncRemote().sendText(String.valueOf(data));
                session.getAsyncRemote().sendText(data.toJSONString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("被拦住了"+session);
            throw new RuntimeException("发送的对象为空");
        }
    }



}