package top.yzlin.yyh;

import top.yzlin.cqrobotsdk.CQRobot;
import top.yzlin.raise.ModianMonitoring;
import top.yzlin.raise.raffle.RafflePrize;
import top.yzlin.raise.raffle.RaffleRaise;
import top.yzlin.raise.raffle.RandomRaise;
import top.yzlin.tools.Tools;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SRModian extends ModianMonitoring {

    public SRModian(String proID, String groupID, CQRobot cqRobot) {
        super(proID, groupID, cqRobot);
        RandomRaise<RafflePrize> randomRaise = new RandomRaise<>();
        RandomRaise<RafflePrize> complete = new RandomRaise<>();
        ArrayList<RafflePrize> completeList = new ArrayList<>(15);
        ArrayList<RafflePrize> allList = new ArrayList<>(15 * 5);
        RafflePrize prize;
        for (int i = 1; i <= 15; i++) {
            prize = new RafflePrize();
            allList.add(prize);
            completeList.add(prize);
            prize.setPrize(i + "号");
            prize.setProbability(20);
            prize.setPicturePath("srcard/" + i + ".jpg");
            for (int j = 1; j <= 4; j++) {
                prize = new RafflePrize();
                allList.add(prize);
                prize.setPrize("碎片" + i + '-' + j);
                prize.setProbability(i <= 10 && j == 1 ? 170 : 160);
                prize.setPicturePath("srcard/images/" + i + "_0" + j + ".jpg");
            }
        }
        randomRaise.setRafflePrizeList(allList);
        complete.setRafflePrizeList(completeList);


        RaffleRaise raffleRaise = new RaffleRaise(this) {
            RandomRaise allRand = randomRaise;//混合
            RandomRaise completeRand = complete;//整图

            @Override
            public String sendText(String name, double money) {
                if (money < minLimit) {
                    return "";
                }
                StringBuilder str = new StringBuilder().append('\n').append(upText).append('\n');
                RafflePrize tr;
                StringBuilder log = new StringBuilder();
                RafflePrize min = RafflePrize.empty;
                Map<RafflePrize, Integer> raise = new HashMap<>();
                int count = (int) (money / 72.9) * 8 + (int) (money % 72.9) / 10;
                if (money >= 333) {
                    count--;
                    tr = completeRand.raffle();
                    raise.put(tr, 1);
                    saveData(name, tr);
                }
                for (int i = 0; i < count; i++) {
                    tr = allRand.raffle();
                    saveData(name, tr);
                    if (tr.getProbability() < min.getProbability()) {
                        min = tr;
                    }
                    raise.put(tr, raise.containsKey(tr) ? raise.get(tr) + 1 : 1);
                }
                raise.entrySet().stream().sorted(Comparator.comparing(o -> o.getKey().getPrize()))
                        .forEach(e -> {
                            str.append(e.getKey().getPrize()).append('×').append(e.getValue()).append('\n');
                            log.append(e.getKey().getPrize()).append('×').append(e.getValue()).append(' ');
                        });
                if (listFunction != null) {
                    str.append(listFunction.apply(min)).append('\n');
                }
                Tools.print(name + "抽到了" + log.toString());
                str.append(downText);
                return str.toString();
            }
        };
        raffleRaise.setMinLimit(1);
        raffleRaise.setPrizeKind("拼图");
        raffleRaise.setListFunction(r -> CQRobot.getImgCQCode(r.getPicturePath()));
        raffleRaise.setText("恭喜聚聚抽到以下拼图", "恭喜！！！");

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
