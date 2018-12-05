package top.yzlin.yyh;

import top.yzlin.cqrobotsdk.CQRobot;
import top.yzlin.cqrobotsdk.cqinfo.GroupMsgInfo;
import top.yzlin.cqrobotsdk.msginterface.reply.GroupMsgReply;
import top.yzlin.raise.Modian;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SRPK implements GroupMsgReply {

    private String groupID;
    private List<List<Modian>> modianList;

    public SRPK(CQRobot cqRobot, String groupID, List<List<Integer>> proList) {
        this.groupID = groupID;
        cqRobot.addMsgSolution(this);
        modianList = proList.parallelStream()
                .map(i -> i.parallelStream()
                        .map(Modian::new)
                        .collect(Collectors.toList())
                ).collect(Collectors.toList());
    }


    @Override
    public boolean fromGroup(String from) {
        return Objects.equals(groupID, from);
    }

    @Override
    public boolean checkMsg(String from) {
        return "战况".equals(from);
    }

    @Override
    public String replyMsg(GroupMsgInfo a) {
        StringBuilder sb = new StringBuilder();
        List<List<Double>> nowList = modianList.parallelStream()
                .map(i -> i.parallelStream()
                        .map(Modian::getNowMoney)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        sb.append("当前战况\n");
        for (int i = 0, l = nowList.size(); i < l; i++) {
            List<Double> temp = nowList.get(i);
            double sum = temp.stream().mapToDouble(Double::doubleValue).sum();
            sb.append('\n').append(i + 1).append('.').append('￥').append(String.format("%.2f", sum)).append('\n');
            for (int j = 0; j < temp.size(); j++) {
                sb.append(j + 1).append(")￥").append(temp.get(j)).append(modianList.get(i).get(j).getTitle()).append('\n');
            }
            temp.clear();
        }
        nowList.clear();
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
