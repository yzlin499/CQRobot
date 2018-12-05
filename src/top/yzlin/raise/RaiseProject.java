package top.yzlin.raise;

import top.yzlin.monitoring.BaseData;
import top.yzlin.tools.Tools;

public abstract class RaiseProject implements BaseData<RaiseData> {

    private String title;
    private double goalMoney;
    private String endTime;
    private String moneyUrl;

    protected void setGoalMoney(double goalMoney) {
        this.goalMoney = goalMoney;
    }

    protected void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getGoalMoney() {
        return goalMoney;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getMoneyUrl() {
        return moneyUrl;
    }

    public void setMoneyUrl(String moneyUrl) {
        this.moneyUrl = Tools.getTinyURL(moneyUrl);
    }

    public abstract double getNowMoney();


}
