package top.yzlin.cqrobotsdk.msginterface.reply;

import top.yzlin.cqrobotsdk.CQRobot;
import top.yzlin.cqrobotsdk.Lemoc;
import top.yzlin.cqrobotsdk.cqinfo.DiscussMsgInfo;
import top.yzlin.cqrobotsdk.cqinfo.GroupMsgInfo;
import top.yzlin.cqrobotsdk.cqinfo.MsgInfo;
import top.yzlin.cqrobotsdk.msginterface.EventSolution;

import java.util.function.Function;

public class ProxyReplySolution implements EventSolution<MsgInfo> {
    private ReplySolution replySolution;
    private Function<MsgInfo, Void> strategy;

    public ProxyReplySolution(CQRobot cqRobot, ReplySolution replySolution, int type) {
        this.replySolution = replySolution;
        switch (type) {
            case Lemoc.GET_GROUP_MSG:
                strategy = m -> {
                    String text = replySolution.replyMsg(m);
                    if (text != null) {
                        cqRobot.sendGroupMsg(((GroupMsgInfo) m).getFromGroup(), text);
                    }
                    return null;
                };
                break;
            case Lemoc.GET_PERSON_MSG:
                strategy = m -> {
                    String text = replySolution.replyMsg(m);
                    if (text != null) {
                        cqRobot.sendPersonMsg(m.getFromQQ(), text);
                    }
                    return null;
                };
                break;
            case Lemoc.GET_DISCUSS_MSG:
                strategy = m -> {
                    String text = replySolution.replyMsg(m);
                    if (text != null) {
                        cqRobot.sendDiscussMsg(((DiscussMsgInfo) m).getFromDiscuss(), text);
                    }
                    return null;
                };
                break;
            default:
                strategy = m-> null;
        }
    }

    @Override
    public void msgSolution(MsgInfo msgInfo) {
        if (replySolution.filter(msgInfo)) {
            strategy.apply(msgInfo);
        }
    }
}
