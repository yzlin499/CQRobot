package top.yzlin.yyh;

import top.yzlin.cqrobotsdk.CQRobot;
import top.yzlin.cqrobotsdk.cqinfo.GroupMemberIncreaseEventInfo;
import top.yzlin.cqrobotsdk.msginterface.GroupMemberIncreaseSolution;

public class SRGroupAdd implements GroupMemberIncreaseSolution {
    private String huanyin = "加入孙芮应援会\n" +
            "这里是瑞士卷的聚集地，进群的都是兄弟姐妹，一起在351门前排队，领着撕纸的号码牌。\n" +
            "请进群的卷卷仔细阅读群规哟。\n" +
            "欢迎关注新浪微博@SNH48-孙芮 及#孙芮# 超级话题:\n" +
            "视频补档请点击：https://m.weibo.cn/status/4302693265070315\n" +
            "欢迎关注B站孙芮视频站点：https://space.bilibili.com/11777755/\n" +
            "孙芮应援会：https://space.bilibili.com/38114193/\n" +
            "孙芮的烤冷面摊：https://space.bilibili.com/20434973/\n" +
            "有不懂的地方，可以随时询问管理。管理们热情又美丽，高大又威猛，迷人又可爱......对应援会有任何建议也欢迎提出。同时也欢迎有意为宣传数据做贡献的瑞士卷加入孙芮网宣组114963130。";

    private String groupID;
    private CQRobot cqRobot;

    public SRGroupAdd(CQRobot cqRobot, String groupID) {
        this.cqRobot = cqRobot;
        cqRobot.addMsgSolution(this);
        this.groupID = groupID;
    }

    @Override
    public void msgSolution(GroupMemberIncreaseEventInfo msg) {
        if (groupID.equals(msg.getFromGroup())) {
            cqRobot.sendGroupMsg(groupID, "欢迎老铁" + CQRobot.getAtCQCode(msg.getFromQQ()) + huanyin);
        }
    }
}
