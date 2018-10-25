package top.yzlin.yyh.teamsiiraffle;

import top.yzlin.raise.AbstractMonitoring;
import top.yzlin.raise.RaiseData;
import top.yzlin.raise.RaiseEvent;
import top.yzlin.raise.raffle.RandomRaise;

import java.util.ArrayList;

public class SIIRaffle implements RaiseEvent {
    private AbstractMonitoring raiseMonitoering;
    private RandomRaise<Prize> randomRaise = new RandomRaise<>();

    public SIIRaffle(AbstractMonitoring raiseMonitoering) {
        this.raiseMonitoering = raiseMonitoering;
        raiseMonitoering.setRaiseEvent(this);
    }

    public void setPrizeList(ArrayList<Prize> prizeList) {
        randomRaise.setRafflePrizeList(prizeList);
    }

    public void setRandomRaise(RandomRaise<Prize> randomRaise) {
        this.randomRaise = randomRaise;
    }


    @Override
    public String eventTrigger(RaiseData raiseData) {

        return null;
    }

}
