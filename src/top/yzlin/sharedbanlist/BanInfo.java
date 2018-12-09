package top.yzlin.sharedbanlist;

import com.alibaba.fastjson.annotation.JSONField;

public class BanInfo {

    private String targetQQ;
    private String fromGroup;
    private String describe;
    private long addTime;

    public String getTargetQQ() {
        return targetQQ;
    }

    @JSONField(name = "target_qq")
    public void setTargetQQ(String targetQQ) {
        this.targetQQ = targetQQ;
    }

    public String getFromGroup() {
        return fromGroup;
    }

    @JSONField(name = "from_group")
    public void setFromGroup(String fromGroup) {
        this.fromGroup = fromGroup;
    }

    public String getDescribe() {
        return describe;
    }

    @JSONField(name = "describe")
    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public long getAddTime() {
        return addTime;
    }

    @JSONField(name = "add_time")
    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

}
