package top.yzlin.cqrobotsdk;

import top.yzlin.cqrobotsdk.msginterface.EventSolution;

public interface CQRobot {

    // <editor-fold desc="各种状态码">
    // <editor-fold desc="接收到的状态码">
    /**
     * 接收群信息
     */
    int GET_GROUP_MSG = 2;
    /**
     * 接收讨论组信息
     */
    int GET_DISCUSS_MSG = 4;
    /**
     * 接收私聊(个人)信息
     */
    int GET_PERSON_MSG = 21;
    /**
     * 管理员变动
     */
    int GET_GROUP_ADMIN_CHANGE = 101;
    /**
     * 群成员增加
     */
    int GET_GROUP_MEMBER_INCREASE = 103;
    /**
     * 群成员减少
     */
    int GET_GROUP_MEMBER_DECREASE = 102;
    /**
     * 好友增加
     */
    int GET_FRIEND_INCREASE = 201;
    /**
     * 好友增加请求
     */
    int GET_FRIEND_REQUEST = 301;
    /**
     * 群增加请求
     */
    int GET_GROUP_REQUEST = 302;
    // </editor-fold>
    /**
     * 删除群成员
     */
    int GROUP_DELETE_MEMBER = 120;
    /**
     * 发送群消息
     */
    int SEND_GROUP_MSG = 101;
    /**
     * 发送讨论组消息
     */
    int SEND_DISCUSS_MSG = 103;
    /**
     * 发送私聊(个人)消息
     */
    int SEND_PERSON_MSG = 106;
    /**
     * 发送赞
     */
    int SEND_PRAISE = 110;
    /**
     * 全群禁言
     */
    int GROUP_BANNED = 123;
    /**
     * 匿名成员禁言
     */
    int ANONYMOUS_MEMBER_BANNED = 124;
    /**
     * 群匿名设置
     */
    int SET_GROUP_ANONYMOUS = 125;
    /**
     * 群成员名片设置
     */
    int SET_MEMBER_CARD = 126;
    /**
     * 群成员专属头衔
     */
    int SET_MEMBER_SPECIAL_TITLE = 128;

    /**
     * json串方式返回群成员信息
     */
    int GET_MEMBER_INFOMATION = 20303;
    /**
     * json串方式返回陌生人信息
     */
    int GET_STRANGER_INFOMATION = 25304;

    /**
     * 这实在是很可恶的一个代码，我实在是不知道怎么去解决
     */
    public static final int GET_GROUP_UPLOAD_FILE = 4444;

    // </editor-fold>

    void sendPersonMsg(String qqID, String msg);

    void sendGroupMsg(String groupID, String msg);

    void sendDiscussMsg(String discussID, String msg);

    boolean addMsgSolution(EventSolution msm);

    boolean removeMsgSolution(EventSolution msm);

    void close();

    /**
     * 获取图片的CQ码<br>
     * 图片存放在酷Q目录的data\image\下<br><br>
     * 举例：<b>getImgCQCode("1.jpg");</b>（发送data\image\1.jpg）<br><br>
     * 发送图片信息的时候的方法为:<b>sendxxxxxMsg(getImgCQCode("xxx"));</b>
     * 会员才能发送图片
     *
     * @param imgFile 图片的名称
     * @return 图片的CQ码
     */
    static String getImgCQCode(String imgFile) {
        return "[CQ:image,file=" + imgFile + "]";
    }

    /**
     * 获取音频的CQ码<br>
     * 图片存放在酷Q目录的data\record\下<br><br>
     * 举例：<b>getImgCQCode("1");</b>（发送data\image\1.jpg）<br><br>
     * 发送图片信息的时候的方法为:<b>sendxxxxxMsg(getImgCQCode("xxx"));</b>
     * 会员才能发送音频
     *
     * @param audioFile 音频的名称
     * @return 音频的CQ码
     */
    static String getAudioCQCode(String audioFile, boolean magic) {
        return "[CQ:record,file=" + audioFile + (magic ? ",magic=true" : "") + "]";
    }

    static String getAtCQCode(String qqID) {
        return "[CQ:at,qq=" + qqID + "]";
    }
}
