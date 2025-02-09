package com.hust.community.dao;

import com.hust.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@Mapper
public interface DiscussPostMapper {
    List<DiscussPost> selectDiscussPost(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);
    int selectDiscussPostRows(@Param("userId") int userId);

    int insertDiscussPost(DiscussPost discussPost);

    DiscussPost selectDiscussPostById(int id);

    int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);
}
