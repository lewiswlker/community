package com.hust.community.dao;

import com.hust.community.entity.Comment;
import com.hust.community.service.CommentService;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@Mapper
public interface CommentMapper {

    List<Comment> selectCommentsByEntity(@Param("entityType") int entityType, @Param("entityId") int entityId,
                                         @Param("offset") int offset, @Param("limit") int limit);

    int selectCountByEntity(@Param("entityType") int entityType, @Param("entityId") int entityId);

    int insertComment(Comment comment);

    Comment selectCommentById(int id);
}
