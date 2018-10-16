package top.yzlin.douyinquery;


import top.yzlin.cqrobotsdk.CQRobot;
import top.yzlin.monitoring.Monitoring;
import top.yzlin.tools.Tools;

public class DouYinMonitoring extends Monitoring<DouYinInfo> {
    private CQRobot cqRoot;
    private String gid;

    public DouYinMonitoring(DouYin douYin,CQRobot cqRoot, String gid){
        super(douYin);
        this.gid = gid;
        this.cqRoot = cqRoot;
        setConsumer(d->{
            for (DouYinInfo douYinInfo : d) {
                pushMsg(douYinInfo);
            }
        });
        setFrequency(1000*60);
        Tools.print(douYin.getMemberName() + "抖音监控启动");
    }

    public DouYinMonitoring(String name,CQRobot cqRoot, String gid) {
        this(new DouYin(name),cqRoot,gid);
    }

    @Override
    protected boolean predicate(DouYinInfo newData, DouYinInfo oldData) {
        return newData.getDouyinID()> oldData.getDouyinID();
    }

    private void pushMsg(DouYinInfo douYinInfo) {
        Tools.print(douYinInfo.getMemberName() + "发了一个抖音" +
                douYinInfo.getTitle());
        cqRoot.sendGroupMsg(gid, sendMsg(douYinInfo));
    }

    protected String sendMsg(DouYinInfo douYinInfo) {
        return douYinInfo.getMemberName() + "发了一个抖音\n" +
                douYinInfo.getTitle();
    }
}
