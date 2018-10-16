package top.yzlin.raise;

import top.yzlin.monitoring.BaseData;
import top.yzlin.tools.Tools;

public abstract class RaiseProject implements BaseData<RaiseData> {

    protected String title;
    protected double goalMoney;
    protected String endTime;
    protected String moneyUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = Tools.getTinyURL(title);
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
        this.moneyUrl = moneyUrl;
    }

    public abstract double getNowMoney();


}
