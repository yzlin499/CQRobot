package top.yzlin.sharedbanlist.cqrobotapi;

import top.yzlin.cqrobotsdk.CQRobot;
import top.yzlin.cqrobotsdk.cqinfo.PersonMsgInfo;
import top.yzlin.cqrobotsdk.msginterface.reply.PersonMsgReply;
import top.yzlin.sharedbanlist.BanInfo;
import top.yzlin.sharedbanlist.BanListAPI;
import top.yzlin.tools.Tools;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BanListOperation implements PersonMsgReply {

    private final OperationInterface[] operationInterfaces = {
            new AddBanList(), new DeleteBanList(), new IsInBanList()
    };

    private BanListAPI banListAPI;
    private Set<String> adminSet;
    private int operation;

    public BanListOperation(String[] admins, BanListAPI banListAPI, CQRobot cqRobot) {
        adminSet = new HashSet<>(Arrays.asList(admins));
        this.banListAPI = banListAPI;
        cqRobot.addMsgSolution(this);
    }

    @Override
    public boolean fromQQ(String from) {
        return adminSet.contains(from);
    }

    @Override
    public boolean checkMsg(String from) {
        for (int i = 0; i < operationInterfaces.length; i++) {
            if (operationInterfaces[i].check(from)) {
                operation = i;
                return true;
            }
        }
        return false;
    }

    @Override
    public String replyMsg(PersonMsgInfo a) {
        if (operation != 2) {
            Tools.print(a.getFromQQ() + "黑名单操作" + a.getMsg());
        }
        return operationInterfaces[operation].reply(this);
    }

    List<BanInfo> isInBanList(String targetQQ) {
        return banListAPI.isInBanList(targetQQ);
    }

    boolean addQQToBanList(String targetQQ, String describe) {
        return banListAPI.addQQToBanList(targetQQ, describe);
    }

    boolean deleteQqFromGroup(String targetQQ) {
        return banListAPI.deleteQqFromGroup(targetQQ);
    }

    List<BanInfo> getBanList() {
        return banListAPI.getBanList();
    }
}
