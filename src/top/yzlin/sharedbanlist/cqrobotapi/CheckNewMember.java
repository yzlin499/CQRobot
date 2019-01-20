package top.yzlin.sharedbanlist.cqrobotapi;

import top.yzlin.cqrobotsdk.CQRobot;
import top.yzlin.cqrobotsdk.cqinfo.GroupMemberIncreaseEventInfo;
import top.yzlin.cqrobotsdk.msginterface.GroupMemberIncreaseSolution;
import top.yzlin.sharedbanlist.BanListAPI;

import java.util.Arrays;
import java.util.HashSet;

public class CheckNewMember implements GroupMemberIncreaseSolution {
    private HashSet<String> adminSet;
    private String groupID;
    private BanListAPI banListAPI;


    public CheckNewMember(String[] admin, CQRobot cqRobot, String groupID, BanListAPI banListAPI) {
        adminSet = new HashSet<>(Arrays.asList(admin));
        cqRobot.addMsgSolution(this);
        this.groupID = groupID;
        this.banListAPI = banListAPI;
    }

    @Override
    public void msgSolution(GroupMemberIncreaseEventInfo msg) {

    }
}
