package com.hust.community.service;

import com.hust.community.dao.UserMapper;
import com.hust.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User findUserById(int userId) {
        return userMapper.selectById(userId);
    }
}
