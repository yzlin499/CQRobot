package top.yzlin.yyh;

import top.yzlin.cqrobotsdk.CQRobot;
import top.yzlin.raise.ModianMonitoring;

public class SRModian extends ModianMonitoring {
    public SRModian(String proID, String groupID, CQRobot cqRobot) {
        super(proID, groupID, cqRobot);
    }

    @Override
    protected String sendText(String name, double money, double nowMoney, String title, double goalMoney, String moneyUrl, String endTime) {
        return "感谢:"+name+"支援了"+money+"元\n"+
                title+"\n"+
                "集资链接:"+moneyUrl+"\n"+
                "已筹:￥"+nowMoney+"/"+goalMoney+'\n'+
                "剩:"+(goalMoney-nowMoney)+'\n'+
                "完成:"+String.format("%.2f",nowMoney/goalMoney*100)+"%\n"+
                ModianMonitoring.progressBar(nowMoney/goalMoney*100)+"\n"+
                "结束时间:"+endTime;
    }
}
