package top.yzlin.cqrobotsdk;

import top.yzlin.tools.Tools;

public class TimingRadio extends Thread{
    private long sleepTime=100000;
    private Lemoc cqRobot;
    private String GID;
    private String radioText;

    public TimingRadio(Lemoc cqRobot, String GID){
        this.cqRobot = cqRobot;
        this.GID=GID;
    }

    public void setSleepTime(long sleepTime) {
        this.sleepTime = sleepTime;
    }

    public void setRadioText(String radioText){
        this.radioText=radioText;
    }

    @Override
    public void run() {
        if(radioText!=null){
            while(true){
                cqRobot.sendGroupMsg(GID,radioText);
                Tools.sleep(sleepTime);
            }
        }
    }
}
