package top.yzlin.yyh;

import top.yzlin.cqrobotsdk.CQRobot;
import top.yzlin.raise.AbstractMonitoring;
import top.yzlin.raise.raffle.ParseCardPool;
import top.yzlin.raise.raffle.RafflePrize;
import top.yzlin.raise.raffle.RaffleRaise;
import top.yzlin.raise.raffle.RandomRaise;
import top.yzlin.tools.Tools;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ZJYRaffle extends RaffleRaise {
    private RafflePrize superPrize = new RafflePrize("万能卡", 1000000);

    public ZJYRaffle(AbstractMonitoring am) {
        super(am);
        init();
    }

    private void init() {
        File file = new File("doc/mumu-card.xml");
        RandomRaise randomRaise = ParseCardPool.getInstance().parseRandomForXml(file)[0];
        setRandomRaise(randomRaise);
        setMinLimit(6);
        setUpText("恭喜获得以下拼图！！！");
        setListFunction(i -> CQRobot.getImgCQCode(i.getPicturePath()));
        setPrizeKind("拼图");
        setDownText("恭喜啊！！！");
    }

    @Override
    public String sendText(String name, double money) {
        if (money < minLimit + 0.001) {
            return "";
        }
        StringBuilder str = new StringBuilder().append('\n').append(upText).append('\n');
        StringBuilder log = new StringBuilder();
        Map<RafflePrize, Integer> rafflePrizeIntegerMap = new HashMap<>();
        RafflePrize tr = null;
        while (money >= (minLimit * 10)) {
            tr = tenRaffle(name, rafflePrizeIntegerMap);
            money -= minLimit * 10;
        }
        while (money >= minLimit - 0.001) {
            tr = raffle();
            saveData(name, tr);
            rafflePrizeIntegerMap.put(tr, rafflePrizeIntegerMap.containsKey(tr) ? rafflePrizeIntegerMap.get(tr) + 1 : 1);
            money -= minLimit;
        }
        rafflePrizeIntegerMap.forEach((k, v) -> {
            str.append(k.getPrize()).append('×').append(v).append('\n');
            log.append(k.getPrize()).append('×').append(v).append('\n');
        });
        if (listFunction != null) {
            str.append(listFunction.apply(tr)).append('\n');
        }
        Tools.print(name + "抽到了" + log.toString());
        str.append(downText);
        return str.toString();
    }

    private RafflePrize tenRaffle(String name, Map<RafflePrize, Integer> rafflePrizeIntegerMap) {
        RafflePrize tr;
        tr = superPrize;
        saveData(name, tr);
        rafflePrizeIntegerMap.put(tr, rafflePrizeIntegerMap.containsKey(tr) ? rafflePrizeIntegerMap.get(tr) + 1 : 1);
        for (int i = 1; i <= 9; i++) {
            tr = raffle();
            saveData(name, tr);
            rafflePrizeIntegerMap.put(tr, rafflePrizeIntegerMap.containsKey(tr) ? rafflePrizeIntegerMap.get(tr) + 1 : 1);
        }
        return tr;
    }

}
