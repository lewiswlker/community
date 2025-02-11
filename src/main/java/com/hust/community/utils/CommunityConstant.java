package com.hust.community.utils;

/**
 * @author Liubo Ren
 * @version 1.0
 */
public interface CommunityConstant {
    /**
     * 激活成功
     */
    int ACTIVATION_SUCCESS = 0;
    /**
     * 重复激活
     */
    int ACTIVATION_REPEAT = 1;
    /**
     * 激活失败
     */
    int ACTIVATION_FAILURE = 2;
    /**
     * 默认状态登录凭证超时时间
     */
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;
    /**
     * 记住我状态下登录凭证超时时间
     */
    int REMEMBER_EXPIRED_SECONDS = 3600 * 24 * 90;
    /**
     * 实体类型：帖子
     */
    int ENTITY_TYPE_POST = 1;
    /**
     * 实体类型：评论
     */
    int ENTITY_TYPE_COMMENT = 2;
    /**
     * 实体类型：人
     */
    int ENTITY_TYPE_USER = 3;
    /**
     * 主题：评论
     */
    String TOPIC_COMMENT = "comment";
    /**
     * 主题：点赞
     */
    String TOPIC_LIKE = "like";
    /**
     * 主题：关注
     */
    String TOPIC_FOLLOW = "follow";
    /**
     * 主题：发布帖子
     */
    String TOPIC_PUBLISH = "publish";
    /**
     * 主题：删除帖子
     */
    String TOPIC_DELETE = "delete";
    /**
     * 系统用户ID
     */
    int SYSTEM_ID = 1;
    /**
     * 权限：普通用户
     */
    String AUTHORITY_USER = "user";
    /**
     * 权限：管理员
     */
    String AUTHORITY_ADMIN = "admin";
    /**
     * 权限：版主
     */
    String AUTHORITY_MODERATOR = "moderator";
    /**
     * 帖子类型：置顶
     */
    int DISCUSSPOST_STATUS_TOP = 1;
    /**
     * 帖子状态：加精
     */
    int DISCUSSPOST_STATUS_WONDERFUL = 1;
    /**
     * 帖子状态：删除
     */
    int DISCUSSPOST_STATUS_DELETE = 2;
    /**
     * 主题：分享
     */
    String TOPIC_SHARE = "share";
}
