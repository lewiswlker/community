package com.hust.community.dao;

import com.hust.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@Mapper
@Deprecated
public interface LoginTicketMapper {
    @Insert({
            "insert into login_ticket (user_id, ticket, status, expired) ",
            "values(#{userId}, #{ticket}, #{status}, #{expired})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public int insertLoginTicket(LoginTicket loginTicket);
    @Select({
            "select id, user_id, ticket, status, expired ",
            "from login_ticket where ticket=#{ticket}"
    })
    public LoginTicket selectByTicket(String ticket);
    @Update({
            "update login_ticket set status=#{status} where ticket=#{ticket}"
    })
    public int updateStatus(@Param("ticket") String ticket, @Param("status") int status);
}
