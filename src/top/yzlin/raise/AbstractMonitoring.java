package top.yzlin.raise;

import top.yzlin.cqrobotsdk.CQRobot;
import top.yzlin.monitoring.Monitoring;
import top.yzlin.tools.Tools;

public class AbstractMonitoring extends Monitoring<RaiseData> {
    //基础信息
    private String groupID;
    private CQRobot cqRobot;

    private RaiseEvent raiseEvent = null;
    private RaiseProject raiseProject;


    protected AbstractMonitoring(RaiseProject raiseProject,String groupID, CQRobot cqRobot) {
        super(raiseProject);
        this.raiseProject=raiseProject;
        this.groupID = groupID;
        this.cqRobot = cqRobot;
        setConsumer(r-> {
            double n=raiseProject.getNowMoney();
            for (RaiseData raiseData : r) {
                sendMsg(raiseData,n);
            }
        });
    }

    @Override
    protected boolean predicate(RaiseData newData, RaiseData oldData) {
        return newData.getPayTime()>oldData.getPayTime();
    }

    final public void setRaiseEvent(RaiseEvent raiseEvent) {
        this.raiseEvent = raiseEvent;
    }

    public String getGroupID() {
        return groupID;
    }

    public CQRobot getCqRobot() {
        return cqRobot;
    }


    /**
     * 发送信息
     *
     * @param raiseData
     */
    final void sendMsg(RaiseData raiseData, double nowMoney) {
        Tools.print("[" + raiseProject.title + "]项目" + raiseData.getNickName() + "资助" + raiseData.getRaiseMoney() + "元");
        cqRobot.sendGroupMsg(groupID,
                sendText(raiseData.getNickName(),
                        raiseData.getRaiseMoney(),
                        nowMoney,
                        raiseProject.title,
                        raiseProject.goalMoney,
                        raiseProject.moneyUrl,
                        raiseProject.endTime) +
                        (raiseEvent == null ? "" : raiseEvent.eventTrigger(raiseData)));
    }


    /**
     * 重写这个方法来自定义发送信息的样式
     *
     * @param name      集资人的名字
     * @param money     集资金额
     * @param nowMoney  当前集资进度
     * @param title     集资项目标题
     * @param goalMoney 目标金额
     * @param moneyUrl  集资链接的短地址
     * @param endTime   结束时间
     * @return 这个方法的返回值会发给这个群
     */
    protected String sendText(String name, double money, double nowMoney, String title, double goalMoney, String moneyUrl, String endTime) {
        return "感谢" + name + "聚聚支持了【" + title + "】" + money + "元\n" +
                "当前进度:" + nowMoney + "元,目标 ：" + goalMoney + "元\n" +
                "集资链接:" + moneyUrl;
    }


    public static String progressBar(double progress) {
        char[] c = {'▏', '▎', '▍', '▌', '▋', '▊', '▉'};
        if (progress >= 100) {
            return "[██████████]";
        }
        char[] r = "[　　　　　　　　　　]".toCharArray();
        for (int i = 1, j = (int) (progress / 10); i <= j; i++) {
            r[i] = '█';
        }
        double d = progress % 10;
        r[(int) (progress / 10) + 1] = c[(int) (d / 1.429)];
        return new String(r);
    }
}
