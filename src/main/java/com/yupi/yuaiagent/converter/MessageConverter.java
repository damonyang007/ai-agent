package com.yupi.yuaiagent.converter;

import com.yupi.yuaiagent.entity.ChatMessage;
import java.util.List;
import java.util.Map;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.messages.UserMessage;

/**
 * @author yangjialin
 */
public class MessageConverter {

  /**
   * 将 Message 转换为 ChatMessage
   */
  public static ChatMessage toChatMessage(Message message, String conversationId) {
    ChatMessage chatMessage = new ChatMessage();
    chatMessage.setConversationId(conversationId);
    chatMessage.setMessageType(message.getMessageType());
    chatMessage.setContent(message.getText());
    chatMessage.setMetadata(message.getMetadata());
    return chatMessage;
  }

  /**
   * 将 ChatMessage 转换为 Message
   */
  public static Message toMessage(ChatMessage chatMessage) {
    MessageType messageType = chatMessage.getMessageType();
    String text = chatMessage.getContent();
    Map<String, Object> metadata = chatMessage.getMetadata();
    return switch (messageType) {
      case USER -> new UserMessage(text);
      case ASSISTANT -> new AssistantMessage(text, metadata);
      case SYSTEM -> new SystemMessage(text);
      case TOOL -> new ToolResponseMessage(List.of(), metadata);
    };
  }

}
