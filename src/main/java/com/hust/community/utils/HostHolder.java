package com.hust.community.utils;

import com.hust.community.entity.User;
import org.springframework.stereotype.Component;

import javax.jws.soap.SOAPBinding;

/**
 * @author Liubo Ren
 * @version 1.0
 * 持有用户信息，用于代替session对象
 */
@Component
public class HostHolder {
    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUser(User user) {
        users.set(user);
    }

    public User getUser() {
        return users.get();
    }

    public void clear() {
        users.remove();
    }

}
