package top.yzlin.yyh;

import top.yzlin.cqrobotsdk.CQRobot;
import top.yzlin.raise.ModianMonitoring;
import top.yzlin.raise.raffle.MoreCardPoolRaffleRaise;
import top.yzlin.raise.raffle.ParseCardPool;

import java.io.File;

public class TeamXRaffle extends ModianMonitoring {

    public TeamXRaffle(String proID, String groupID, CQRobot cqRobot) {
        super(proID, groupID, cqRobot);
        File file=new File("doc/teamx-card.xml");
        MoreCardPoolRaffleRaise raffleRaise=new MoreCardPoolRaffleRaise(this);
        raffleRaise.addRandomRaise(ParseCardPool.getInstance().parseRandomForXml(file));
        raffleRaise.setText("恭喜聚聚抽到以下卡牌","YEAH TIGHT！！");
        raffleRaise.setPrizeKind("卡牌");
        raffleRaise.setListFunction(rafflePrize -> CQRobot.getImgCQCode(rafflePrize.getPicturePath()));
    }
}
