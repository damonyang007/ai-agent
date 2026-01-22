package com.yupi.yuaiagent.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yupi.yuaiagent.entity.ChatMessage;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yangjialin
 */
@Mapper
public interface ChatMessageMapper extends BaseMapper<ChatMessage> {
}
