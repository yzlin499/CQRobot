package top.yzlin.cqrobotsdk.msginterface.reply;

import top.yzlin.cqrobotsdk.cqinfo.MsgInfo;

public interface ReplySolution<T extends MsgInfo> {
    boolean filter(T from);

    String replyMsg(T a);
}
