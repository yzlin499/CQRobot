package top.yzlin.yyh;

import top.yzlin.cqrobotsdk.CQRobot;
import top.yzlin.tools.Tools;
import top.yzlin.weibo.WeiBo;
import top.yzlin.weibo.WeiBoInfo;
import top.yzlin.weibo.WeiBoMonitoring;

public class XCCWeiBo extends WeiBoMonitoring {
    public XCCWeiBo(String name, String groupID, CQRobot cqRobot) {
        super(name, groupID, cqRobot);
    }

    public XCCWeiBo(WeiBo weiBo, String groupID, CQRobot cqRobot) {
        super(weiBo, groupID, cqRobot);
    }

    @Override
    protected String sendText(WeiBoInfo weiBoInfo) {
        if(weiBoInfo.isRepost()){
            return "可爱的仙女C转发了一条微博:\n" +
                    weiBoInfo.getText()+'\n'+
                    "原文:" + weiBoInfo.getRepostText()+'\n'+
                    "链接:" + Tools.getTinyURL(weiBoInfo.getUrl())+'\n'+
                    "转评赞走起~";
        }else {
            return "可爱的仙女C发了一条微博:\n" +
                    weiBoInfo.getText()+'\n'+
                    "链接:" + Tools.getTinyURL(weiBoInfo.getUrl())+'\n'+
                    "转评赞走起~";
        }
    }
}
