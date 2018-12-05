package top.yzlin.raise.raffle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class RandomRaise<T extends RafflePrize> {

    private static final Random RAND = new Random();
    private int probabilitySum;
    private double minLimit;
    private ArrayList<T> rafflePrizeList = new ArrayList<>();

    public double getMinLimit() {
        return minLimit;
    }

    public void setMinLimit(double minLimit) {
        this.minLimit = minLimit;
    }

    public int getProbabilitySum() {
        return probabilitySum;
    }

    public Random getRand() {
        return RAND;
    }

    public ArrayList<T> getRafflePrizeList() {
        return rafflePrizeList;
    }

    public void setRafflePrizeList(ArrayList<T> rafflePrizeList) {
        this.rafflePrizeList = rafflePrizeList;
        probabilitySum = rafflePrizeList.stream()
                .mapToInt(RafflePrize::getProbability)
                .sum();
    }

    public void addRafflePrize(T... prize) {
        rafflePrizeList.addAll(Arrays.asList(prize));
        probabilitySum = rafflePrizeList.stream()
                .mapToInt(RafflePrize::getProbability)
                .sum();
    }


    public T raffle() {
        int rand = RAND.nextInt(probabilitySum) + 1;
        int tempCount = 0;
        for (T tempPrize : rafflePrizeList) {
            //随机位置
            tempCount += tempPrize.getProbability();
            if (tempCount >= rand) {
                return tempPrize;
            }
        }
        return rafflePrizeList.get(RAND.nextInt(rafflePrizeList.size()));
    }

    @Override
    public String toString() {
        return "RandomRaise{" +
                "probabilitySum=" + probabilitySum +
                ", minLimit=" + minLimit +
                ", rafflePrizeList=" + rafflePrizeList +
                '}';
    }
}
