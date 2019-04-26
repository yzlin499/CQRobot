package top.yzlin.yyh;

import top.yzlin.cqrobotsdk.CQRobot;
import top.yzlin.raise.ModianMonitoring;
import top.yzlin.raise.raffle.ParseCardPool;
import top.yzlin.raise.raffle.RaffleRaise;
import top.yzlin.raise.raffle.RandomRaise;

import java.io.File;

public class TeamXRaffle extends ModianMonitoring {

    public TeamXRaffle(String proID, String groupID, CQRobot cqRobot) {
        super(proID, groupID, cqRobot);
        File file = new File("doc/mumu-card.xml");
        RaffleRaise raffleRaise = new RaffleRaise(this);
        RandomRaise randomRaise = ParseCardPool.getInstance().parseRandomForXml(file)[0];
        raffleRaise.setRandomRaise(randomRaise);
        raffleRaise.setText("恭喜聚聚抽到以下拼图", "恭喜！！");
        raffleRaise.setPrizeKind("拼图");
        raffleRaise.setListFunction(rafflePrize -> CQRobot.getImgCQCode(rafflePrize.getPicturePath()));
    }
}
