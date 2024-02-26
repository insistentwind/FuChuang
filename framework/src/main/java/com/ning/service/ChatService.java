package com.ning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ning.domain.entity.Chat;

import java.util.List;

/**
 * (Chat)表服务接口
 *
 * @author makejava
 * @since 2024-02-25 22:13:25
 */
public interface ChatService extends IService<Chat> {

    /**
     * 根据id判断是否已经存在了聊天数据, 如果存在 则不用重新写入认证信息
     * @param uID
     * @param contactID
     * @return
     */
    boolean getChatExistById(Integer uID, Integer contactID);

    /**
     * 将当前用户所有的聊天数据返回至前端
     * @param uID
     * @return
     */
    List<Chat> getChatById(Integer uID);

    /**
     * 获取所有未读的消息列表
     * @param uID
     * @return
     */
    List<Chat> getChatState(Integer uID);

    /**
     * 修改消息状态
     * @param recvID
     * @param sendID
     */
    void changeChatState(String sendID, Integer recvID);
}

