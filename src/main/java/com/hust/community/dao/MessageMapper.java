package com.hust.community.dao;

import com.hust.community.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@Mapper
public interface MessageMapper {

    // 查询用户会话列表，需要针对每个对话返回最新的消息内容
    List<Message> selectConversations(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);

    // 查询当前用户会话数量
    int selectConversationCount(int userId);

    // 查询某个会话包含的私信列表
    List<Message> selectLetters(@Param("conversationId") String conversationId, @Param("offset") int offset, @Param("limit") int limit);

    int selectLetterCounts(String conversationId);

    int selectLetterUnreadCount(@Param("userId") int userId, @Param("conversationId") String conversationId);

    // 新增消息
    int insertMessage(Message message);

    int updateStatus(@Param("ids") List<Integer> ids, @Param("status") int status);

    // 查询某个主题下最新的通知
    Message selectLatestNotice(@Param("userId") int userId, @Param("topic") String topic);

    // 查询某个主题通知数量
    int selectNoticeCount(@Param("userId") int userId, @Param("topic") String topic);

    // 查询未读通知数量
    int selectNoticeUnreadCount(@Param("userId") int userId, @Param("topic") String topic);

    // 查询某个主题包含的通知列表
    List<Message> selectNotices(@Param("userId") int userId, @Param("topic") String topic, @Param("offset") int offset, @Param("limit") int limit);

}
