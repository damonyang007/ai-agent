package com.yupi.yuaiagent.repository;

import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.yupi.yuaiagent.dao.ChatMessageMapper;
import com.yupi.yuaiagent.entity.ChatMessage;
import org.springframework.stereotype.Component;

/**
 * @author yangjialin
 */
@Component
public class ChatMessageRepository extends CrudRepository<ChatMessageMapper, ChatMessage> {
}