package top.yzlin.cqrobotsdk;

import top.yzlin.cqrobotsdk.cqinfo.GroupMsgInfo;
import top.yzlin.cqrobotsdk.msginterface.GroupMsgSolution;

import java.util.Objects;

public class RereadMachine {
    private int count = 1;
    private int threshold = 4;
    private String lastRecord = "";
    private Lemoc cqRobot;
    private String gid;

    public RereadMachine(Lemoc cqRobot, String gid) {
        this.gid = gid;
        this.cqRobot = cqRobot;
        cqRobot.addMsgSolution((GroupMsgSolution) this::reread);
    }

    private void reread(GroupMsgInfo Msg) {
        if (Objects.equals(gid, Msg.getFromGroup())) {
            String newMsg = Msg.getMsg();
            if (Objects.equals(lastRecord, newMsg)) {
                count++;
                if (count == threshold) {
                    cqRobot.sendGroupMsg(gid, lastRecord);
                    count++;
                }
            } else {
                lastRecord = newMsg;
                count = 1;
            }
        }
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
}
