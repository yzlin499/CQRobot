package top.yzlin.yyh;

import top.yzlin.cqrobotsdk.CQRobot;
import top.yzlin.cqrobotsdk.cqinfo.GroupMsgInfo;
import top.yzlin.cqrobotsdk.msginterface.reply.GroupMsgReply;

public class ZJYYYHMissMuMu implements GroupMsgReply {
    private String lastMiss = null;

    public ZJYYYHMissMuMu(CQRobot cqRobot) {
        cqRobot.addMsgSolution(this);
    }

    @Override
    public boolean fromGroup(String from) {
        return "531501258".equals(from);
    }

    @Override
    public boolean checkMsg(String from) {
        return "想沐沐".equals(from);
    }

    @Override
    public String replyMsg(GroupMsgInfo a) {
        if (lastMiss == null) {
            lastMiss = a.getNick();
            return "张嘉予超可爱 超绝可爱张嘉予 LOVE LOVELY张嘉予";
        } else {

        }
        return null;
    }
}
