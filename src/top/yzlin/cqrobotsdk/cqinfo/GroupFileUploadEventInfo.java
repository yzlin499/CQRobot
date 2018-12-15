package top.yzlin.cqrobotsdk.cqinfo;

public class GroupFileUploadEventInfo extends EventInfo {
    private String fromGroup;
    private String fileName;

    public String getFromGroup() {
        return fromGroup;
    }

    public void setFromGroup(String fromGroup) {
        this.fromGroup = fromGroup;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
