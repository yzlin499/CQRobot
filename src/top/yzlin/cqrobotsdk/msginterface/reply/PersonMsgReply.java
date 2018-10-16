package top.yzlin.cqrobotsdk.msginterface.reply;

import top.yzlin.cqrobotsdk.cqinfo.PersonMsgInfo;
import top.yzlin.cqrobotsdk.msginterface.PersonMsgSolution;

public interface PersonMsgReply extends PersonMsgSolution, ReplySolution<PersonMsgInfo> {
    /**
     * 方法无效
     *
     * @param msgInfo
     */
    @Override
    default void msgSolution(PersonMsgInfo msgInfo) {
    }

    @Override
    default boolean filter(PersonMsgInfo from) {
        return fromQQ(from.getFromQQ()) && checkMsg(from.getMsg());
    }

    default boolean fromQQ(String from) {
        return true;
    }

    default boolean checkMsg(String from) {
        return true;
    }

    @Override
    String replyMsg(PersonMsgInfo a);
}
