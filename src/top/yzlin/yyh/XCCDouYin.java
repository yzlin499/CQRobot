package top.yzlin.yyh;

import top.yzlin.cqrobotsdk.CQRobot;
import top.yzlin.douyinquery.DouYin;
import top.yzlin.douyinquery.DouYinInfo;
import top.yzlin.douyinquery.DouYinMonitoring;
import top.yzlin.tools.Tools;

public class XCCDouYin extends DouYinMonitoring {
    public XCCDouYin(DouYin douYin, CQRobot cqRoot, String gid) {
        super(douYin, cqRoot, gid);
    }

    public XCCDouYin(String name, CQRobot cqRoot, String gid) {
        super(name, cqRoot, gid);
    }

    @Override
    protected String sendMsg(DouYinInfo douYinInfo) {
        return "宇宙最强歌姬仙子徐晨辰发了一条抖音\n"+
                "标题:"+douYinInfo.getTitle()+'\n'+
                "地址:"+Tools.getTinyURL(douYinInfo.getVideoUrl());
    }
}
