package top.yzlin.cqrobotsdk;

import top.yzlin.cqrobotsdk.cqinfo.GroupMsgInfo;
import top.yzlin.cqrobotsdk.msginterface.GroupMsgSolution;

import java.util.Objects;

public class RereadMachine {
    private int count = 1;
    private int threshold = 4;
    private int breakCount = -1;
    private String lastRecord = "";
    private String breakMsg = "断";
    private CQRobot cqRobot;
    private String gid;

    public RereadMachine(CQRobot cqRobot, String gid) {
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
                } else if (count == breakCount) {
                    cqRobot.sendGroupMsg(gid, breakMsg);
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

    public int getBreakCount() {
        return breakCount;
    }

    public void setBreakCount(int breakCount) {
        this.breakCount = breakCount;
    }

    public String getBreakMsg() {
        return breakMsg;
    }

    public void setBreakMsg(String breakMsg) {
        this.breakMsg = breakMsg;
    }
}
