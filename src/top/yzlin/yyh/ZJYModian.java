package top.yzlin.yyh;

import top.yzlin.cqrobotsdk.CQRobot;
import top.yzlin.raise.ModianMonitoring;

public class ZJYModian extends ModianMonitoring {

    private String title;

    public ZJYModian(String proID, String groupID, CQRobot cqRobot) {
        super(proID, groupID, cqRobot);
        title=getTitle();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    protected String sendText(String name, double money, double nowMoney, String title, double goalMoney, String moneyUrl, String endTime) {
        return  "感谢"+name+"同学支持了【"+this.title+"】"+money+"元\n" +
                "当前进度:" + nowMoney + "元,目标:" + goalMoney + "元\n"+
                "集资链接:"+moneyUrl;
    }
}
