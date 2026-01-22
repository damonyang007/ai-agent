package com.yupi.yuaiagent.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import lombok.Data;
import org.springframework.ai.chat.messages.MessageType;

/**
 * @author yangjialin
 */
@Data
@TableName(value = "chat_message", autoResultMap = true)
public class ChatMessage implements Serializable {

  @Serial
  @TableField(exist = false)
  private static final long serialVersionUID = 1L;

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  /**
   * 会话ID
   */
  @TableField("conversation_id")
  private String conversationId;

  /**
   * 消息类型
   */

  @TableField("message_type")
  private MessageType messageType;

  /**
   * 消息内容
   */
  @TableField("content")
  private String content;

  /**
   * 元数据
   */
  @TableField(value = "metadata", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> metadata;

  /**
   * 创建时间
   */
  @TableField(value = "create_time", fill = FieldFill.INSERT)
  private Date createTime;

  /**
   * 更新时间
   */
  @Version
  @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
  private Date updateTime;

  /**
   * 是否删除 0-未删除 1-已删除
   */
  @TableField("is_delete")
  @TableLogic(value = "0", delval = "1")
  private Integer isDelete;

}
