package top.yzlin.cqrobotsdk.msginterface.reply;

import top.yzlin.cqrobotsdk.cqinfo.GroupMsgInfo;
import top.yzlin.cqrobotsdk.msginterface.GroupMsgSolution;

/**
 * 群信息回复
 */
public interface GroupMsgReply extends GroupMsgSolution, ReplySolution<GroupMsgInfo> {
    /**
     * 方法无效
     *
     * @param msgInfo
     */
    @Override
    default void msgSolution(GroupMsgInfo msgInfo) {
    }

    @Override
    default boolean filter(GroupMsgInfo from) {
        return fromQQ(from.getFromQQ()) &&
                fromGroup(from.getFromGroup()) &&
                checkMsg(from.getMsg());
    }

    default boolean fromQQ(String from) {
        return true;
    }

    default boolean fromGroup(String from) {
        return true;
    }

    default boolean checkMsg(String from) {
        return true;
    }

    @Override
    String replyMsg(GroupMsgInfo a);
}
