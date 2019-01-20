package top.yzlin.cqrobotsdk.msginterface.reply;

import top.yzlin.cqrobotsdk.CQRobot;
import top.yzlin.cqrobotsdk.Lemoc;
import top.yzlin.cqrobotsdk.cqinfo.DiscussMsgInfo;
import top.yzlin.cqrobotsdk.cqinfo.GroupMsgInfo;
import top.yzlin.cqrobotsdk.cqinfo.MsgInfo;
import top.yzlin.cqrobotsdk.msginterface.EventSolution;

import java.util.function.Consumer;

public class ProxyReplySolution implements EventSolution<MsgInfo> {
    private ReplySolution replySolution;
    private Consumer<MsgInfo> strategy;

    public ProxyReplySolution(CQRobot cqRobot, ReplySolution replySolution, int type) {
        this.replySolution = replySolution;
        switch (type) {
            case Lemoc.GET_GROUP_MSG:
                strategy = m -> {
                    String text = replySolution.replyMsg(m);
                    if (text != null) {
                        cqRobot.sendGroupMsg(((GroupMsgInfo) m).getFromGroup(), text);
                    }
                };
                break;
            case Lemoc.GET_PERSON_MSG:
                strategy = m -> {
                    String text = replySolution.replyMsg(m);
                    if (text != null) {
                        cqRobot.sendPersonMsg(m.getFromQQ(), text);
                    }
                };
                break;
            case Lemoc.GET_DISCUSS_MSG:
                strategy = m -> {
                    String text = replySolution.replyMsg(m);
                    if (text != null) {
                        cqRobot.sendDiscussMsg(((DiscussMsgInfo) m).getFromDiscuss(), text);
                    }
                };
                break;
            default:
                strategy = m -> {
                };
        }
    }

    @Override
    public void msgSolution(MsgInfo msgInfo) {
        if (replySolution.filter(msgInfo)) {
            strategy.accept(msgInfo);
        }
    }
}
