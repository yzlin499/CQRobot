package top.yzlin.sharedbanlist.cqrobotapi;

import top.yzlin.cqrobotsdk.CQRobot;
import top.yzlin.cqrobotsdk.cqinfo.GroupMemberIncreaseEventInfo;
import top.yzlin.cqrobotsdk.msginterface.GroupMemberIncreaseSolution;
import top.yzlin.sharedbanlist.BanInfo;
import top.yzlin.sharedbanlist.BanListAPI;
import top.yzlin.tools.Tools;

import java.util.List;
import java.util.Objects;

public class CheckNewMember implements GroupMemberIncreaseSolution {
    private String[] admins;
    private String groupID;
    private BanListAPI banListAPI;
    private CQRobot cqRobot;

    public CheckNewMember(String[] admins, CQRobot cqRobot, String groupID, BanListAPI banListAPI) {
        this.admins = admins;
        this.groupID = groupID;
        this.banListAPI = banListAPI;
        this.cqRobot = cqRobot;
        cqRobot.addMsgSolution(this);
    }

    @Override
    public void msgSolution(GroupMemberIncreaseEventInfo msg) {
        if (Objects.equals(msg.getFromGroup(), groupID)) {
            List<BanInfo> banInfoList = banListAPI.isInBanList(msg.getFromQQ());
            if (banInfoList.size() > 0) {
                for (String admin : admins) {
                    cqRobot.sendPersonMsg(admin, "QQ号为:" + msg.getFromQQ() + "有记录\n"
                            + IsInBanList.replyWord(banInfoList));
                    Tools.sleep(500);
                }
            }
        }
    }
}
