package com.hust.community.dao;

import com.hust.community.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@Mapper
public interface UserMapper {
    User selectById(int id);
    User selectByName(String username);
    User selectByEmail(String email);
    int insertUser(User user);
    int updateStatus(@Param("id") int id, @Param("status") int status);
    int updateHeader(@Param("id") int id, @Param("headerUrl") String headerUrl);
    int updatePassword(@Param("id") int id, @Param("password") String password);
}
