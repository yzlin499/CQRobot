package top.yzlin.cqrobotsdk.msginterface.reply;

import top.yzlin.cqrobotsdk.cqinfo.DiscussMsgInfo;
import top.yzlin.cqrobotsdk.msginterface.DiscussMsgSolution;

public interface DiscussMsgReply extends DiscussMsgSolution, ReplySolution<DiscussMsgInfo> {

    /**
     * 方法无效
     *
     * @param msgInfo
     */
    @Override
    default void msgSolution(DiscussMsgInfo msgInfo) {
    }

    @Override
    default boolean filter(DiscussMsgInfo from) {
        return fromQQ(from.getFromQQ()) &&
                fromDiscuss(from.getFromDiscuss()) &&
                checkMsg(from.getMsg());
    }

    default boolean fromQQ(String from) {
        return true;
    }

    default boolean fromDiscuss(String from) {
        return true;
    }

    default boolean checkMsg(String from) {
        return true;
    }

    @Override
    String replyMsg(DiscussMsgInfo a);
}
