import top.yzlin.cqrobotsdk.CQRobot;
import top.yzlin.cqrobotsdk.Lemoc;
import top.yzlin.cqrobotsdk.RereadMachine;
import top.yzlin.cqrobotsdk.cqinfo.GroupMsgInfo;
import top.yzlin.cqrobotsdk.msginterface.reply.GroupMsgReply;

import java.util.Objects;

public class BangGe {
    public static void main(String[] args) {
        String gid = "730074397";
        CQRobot cqRobot = new Lemoc(25303);
        RereadMachine rereadMachine = new RereadMachine(cqRobot, gid);
        rereadMachine.setThreshold(3);

        cqRobot.addMsgSolution(new GroupMsgReply() {
            private String lastMsg;

            @Override
            public boolean fromGroup(String from) {
                return gid.equals(from);
            }

            @Override
            public boolean checkMsg(String from) {
                if (Objects.equals(from, lastMsg)) {
                    return false;
                } else {
                    lastMsg = from;
                    return from.contains("棒哥");
                }
            }

            @Override
            public String replyMsg(GroupMsgInfo a) {
                return a.getMsg();
            }
        });

        cqRobot.addMsgSolution(new GroupMsgReply() {

            @Override
            public boolean fromGroup(String from) {
                return gid.equals(from);
            }

            @Override
            public boolean checkMsg(String from) {
                return from.contains("[CQ:at,qq=404132488]");
            }

            @Override
            public String replyMsg(GroupMsgInfo a) {
                return "[CQ:at,qq=" + a.getFromQQ() + "]棒哥也是你能艾特的？我不想看见第二次，请大家注意自己的身份！！！";
            }
        });
    }
}
