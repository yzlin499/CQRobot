package top.yzlin.sharedbanlist;

import com.alibaba.fastjson.annotation.JSONField;

public class BanInfo {

//    int id;
//    target_qq	long		目标QQ
//    from_group	long		来源群号
//    describe	string		原因描述
//    add_time	long		添加时间
//    status	int

    private int id;
    private String targetQQ;
    private String fromGroup;
    private String describe;
    private long addTime;
    private int status;

    public int getId() {
        return id;
    }

    @JSONField(name = "id")
    public void setId(int id) {
        this.id = id;
    }

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

    public int getStatus() {
        return status;
    }

    @JSONField(name = "status")
    public void setStatus(int status) {
        this.status = status;
    }
}
