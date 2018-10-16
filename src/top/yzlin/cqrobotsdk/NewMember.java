package top.yzlin.cqrobotsdk;

import top.yzlin.cqrobotsdk.cqinfo.GroupMemberIncreaseEventInfo;
import top.yzlin.cqrobotsdk.cqinfo.PersonMsgInfo;
import top.yzlin.cqrobotsdk.msginterface.GroupMemberIncreaseSolution;
import top.yzlin.cqrobotsdk.msginterface.reply.PersonMsgReply;

import java.util.HashSet;
import java.util.Objects;

public class NewMember {
    private Lemoc mt;
    private String GID;
    private HashSet<String> adminSet=new HashSet<>();

    private String newMember = "";

    public NewMember(Lemoc mt, String GID){
        this.mt=mt;
        this.GID=GID;
        mt.addMsgSolution((GroupMemberIncreaseSolution) this::groupMemberIncrease);
        mt.addMsgSolution(new PersonMsgReply() {

            @Override
            public boolean fromQQ(String from) {
                return adminSet.contains(from);
            }

            @Override
            public boolean checkMsg(String msg) {
                return msg.indexOf("设置群新成员消息") == 0 && (msg.charAt(8) == ':' || msg.charAt(8) == '：');
            }

            @Override
            public String replyMsg(PersonMsgInfo a) {
                newMember = a.getMsg().substring(9);
                return "欢迎词设置成功";
            }
        });
    }

    public String getNewMember() {
        return newMember;
    }

    public void setNewMember(String newMember) {
        this.newMember = newMember;
    }

    private void groupMemberIncrease(GroupMemberIncreaseEventInfo Msg) {
        if (Objects.equals(GID, Msg.getFromGroup())) {
            mt.sendGroupMsg(GID, newMember);
        }
    }

    public void addAdmin(String admin) {
        adminSet.add(admin);
    }

}
