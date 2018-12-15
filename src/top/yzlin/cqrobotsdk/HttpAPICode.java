package top.yzlin.cqrobotsdk;

public interface HttpAPICode {
    /**
     * 发送私聊(个人)消息
     */
    String SEND_PERSON_MSG = "send_private_msg";
    /**
     * 发送群消息
     */
    String SEND_GROUP_MSG = "send_group_msg";
    /**
     * 发送讨论组消息
     */
    String SEND_DISCUSS_MSG = "send_discuss_msg";
    /**
     * 撤回消息
     */
    String DELETE_MSG = "delete_msg";
    /**
     * 发送好友赞
     */
    String SEND_LIKE = "send_like";
    /**
     * 群组踢人
     */
    String SET_GROUP_KICK = "set_group_kick";
    /**
     * 群组单人禁言
     */
    String SET_GROUP_BAN = "set_group_ban";
    /**
     * 群组匿名用户禁言
     */
    String SET_GROUP_ANONYMOUS_BAN = "set_group_anonymous_ban";
    /**
     * 群组全员禁言
     */
    String SET_GROUP_WHOLE_BAN = "set_group_whole_ban";
    /**
     * 群组设置管理员
     */
    String SET_GROUP_ADMIN = "set_group_admin";
    /**
     * 群组匿名
     */
    String SET_GROUP_ANONYMOUS = "set_group_anonymous";
    /**
     * 设置群名片（群备注）
     */
    String SET_GROUP_CARD = "set_group_card";
    /**
     * 退出群组
     */
    String SET_GROUP_LEAVE = "set_group_leave";
    /**
     * 设置群组专属头衔
     */
    String SET_GROUP_SPECIAL_TITLE = "set_group_special_title";
    /**
     * 退出讨论组
     */
    String SET_DISCUSS_LEAVE = "set_discuss_leave";
    /**
     * 处理加好友请求
     */
    String SET_FRIEND_ADD_REQUEST = "set_friend_add_request";
    /**
     * 处理加群请求/邀请
     */
    String SET_GROUP_ADD_REQUEST = "set_group_add_request";

}
