package com.ning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ning.domain.entity.Chat;
import com.ning.domain.systemConstants.SystemConstants;
import com.ning.mapper.ChatMapper;
import com.ning.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Chat)表服务实现类
 *
 * @author makejava
 * @since 2024-02-25 22:13:25
 */
@Service("chatService")
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat> implements ChatService {
    @Autowired
    private ChatMapper chatMapper;
    /**
     * 根据id判断是否已经存在了聊天数据, 如果存在 则不用重新写入认证信息
     * @param uID
     * @param contactID
     * @return
     */
    @Override
    public boolean getChatExistById(Integer uID, Integer contactID) {
        LambdaQueryWrapper<Chat> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Chat::getSenderId,uID).eq(Chat::getRecvId,contactID);
        return chatMapper.selectCount(wrapper) > 0;
    }

    /**
     * 将当前用户所有的聊天数据返回至前端
     * @param uID
     * @return
     */
    @Override
    public List<Chat> getChatById(Integer uID) {
        LambdaQueryWrapper<Chat> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Chat::getSenderId,uID).or().eq(Chat::getRecvId,uID);
        return chatMapper.selectList(wrapper);
    }

    /**
     * 获取所有未读的消息列表
     * @param uID
     * @return
     */
    @Override
    public List<Chat> getChatState(Integer uID) {
        LambdaQueryWrapper<Chat> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Chat::getRecvId,uID)
                .eq(Chat::getStatus, SystemConstants.CHAT_NO_READ);
        return chatMapper.selectList(wrapper);
    }

    /**
     * 修改消息状态
     * @param recvID
     * @param sendID
     */
    @Override
    public void changeChatState(String sendID, Integer recvID) {
        Chat chat = new Chat();
        chat.setRecvId(Integer.valueOf(sendID));
        chat.setSenderId(recvID);

    }


}

