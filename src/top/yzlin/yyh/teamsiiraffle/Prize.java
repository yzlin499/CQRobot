package top.yzlin.yyh.teamsiiraffle;

import top.yzlin.raise.raffle.RafflePrize;

import java.util.Objects;

public class Prize extends RafflePrize {
    private int score;

    public Prize() {
        super();
    }

    public Prize(String prize, int probability) {
        super(prize, probability);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        if (!(o instanceof Prize)) {
            return false;
        }
        Prize prize = (Prize) o;
        return score == prize.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), score);
    }
}
