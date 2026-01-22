package com.yupi.yuaiagent.chatmemory;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yupi.yuaiagent.converter.MessageConverter;
import com.yupi.yuaiagent.entity.ChatMessage;
import com.yupi.yuaiagent.repository.ChatMessageRepository;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Component;

/**
 * @author yangjialin
 */
@Component
@RequiredArgsConstructor
public class DatabaseChatMemory implements ChatMemory {

  private final ChatMessageRepository chatMessageRepository;

  @Override
  public void add(String conversationId, Message message) {
    add(conversationId, List.of(message));
  }

  @Override
  public void add(String conversationId, List<Message> messages) {
    if (messages == null || messages.isEmpty()) {
      return;
    }
    List<ChatMessage> chatMessages = messages.stream()
        .map(message -> MessageConverter.toChatMessage(message, conversationId))
        .collect(Collectors.toList());
    chatMessageRepository.saveBatch(chatMessages, chatMessages.size());
  }

  @Override
  public List<Message> get(String conversationId) {
    LambdaQueryWrapper<ChatMessage> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(ChatMessage::getConversationId, conversationId)
        .orderByAsc(ChatMessage::getCreateTime);
    List<ChatMessage> chatMessages = chatMessageRepository.list(queryWrapper);
    return chatMessages.stream()
        .map(MessageConverter::toMessage)
        .collect(Collectors.toList());
  }

  public List<Message> get(String conversationId, int lastN) {
    if (lastN <= 0) {
      return get(conversationId);
    }
    LambdaQueryWrapper<ChatMessage> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(ChatMessage::getConversationId, conversationId)
        .orderByDesc(ChatMessage::getCreateTime)
        .last("LIMIT " + lastN);
    List<ChatMessage> chatMessages = chatMessageRepository.list(queryWrapper);
    if (!chatMessages.isEmpty()) {
      Collections.reverse(chatMessages);
    }
    return chatMessages.stream()
        .map(MessageConverter::toMessage)
        .collect(Collectors.toList());
  }

  @Override
  public void clear(String conversationId) {
    LambdaQueryWrapper<ChatMessage> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(ChatMessage::getConversationId, conversationId);
    chatMessageRepository.remove(queryWrapper);
  }
}
