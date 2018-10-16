package top.yzlin.weibo;

import top.yzlin.cqrobotsdk.CQRobot;
import top.yzlin.monitoring.Monitoring;
import top.yzlin.tools.Tools;

import java.util.Objects;

public class WeiBoMonitoring extends Monitoring<WeiBoInfo> {
    private String groupID;
    private CQRobot cqRobot;
    private String name;

    public WeiBoMonitoring(String name,String groupID,CQRobot cqRobot){
        this(new WeiBo(name),groupID,cqRobot);
    }

    public WeiBoMonitoring(WeiBo weiBo, String groupID, CQRobot cqRobot) {
        super(weiBo);
        this.groupID = groupID;
        this.cqRobot = cqRobot;
        name = weiBo.getName();
        setConsumer(w->{
            for (WeiBoInfo weiBoInfo : w) {
                sendMsg(weiBoInfo);
            }
        });
    }


    @Override
    protected boolean predicate(WeiBoInfo newData, WeiBoInfo oldData) {
        return ((newData.getDate().getTime() - oldData.getDate().getTime()) > (1000 * 150)) && (!Objects.equals(newData, oldData));
    }

    private void sendMsg(WeiBoInfo weiBoInfo) {
        Tools.print(name + "发了微博");
        cqRobot.sendGroupMsg(groupID, sendText(weiBoInfo));
    }

    protected String sendText(WeiBoInfo weiBoInfo) {
        if (weiBoInfo.isRepost()) {
            return name + "转发了一条微博:\n" +
                    weiBoInfo.getText() + '\n' +
                    "原文:" + weiBoInfo.getRepostText() + '\n' +
                    "链接:" + Tools.getTinyURL(weiBoInfo.getUrl());
        } else {
            return name + "发了一条微博:\n" +
                    weiBoInfo.getText() + '\n' +
                    "链接:" + Tools.getTinyURL(weiBoInfo.getUrl());
        }
    }
}
