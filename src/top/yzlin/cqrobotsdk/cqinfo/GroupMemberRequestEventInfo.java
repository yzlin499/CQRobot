package top.yzlin.cqrobotsdk.cqinfo;

final public class GroupMemberRequestEventInfo extends EventInfo {
    final public static int SUBTYPE_APPLY = 1;
    final public static int SUBTYPE_OWN_APPLY = 2;

    private String formGroup;
    private String msg;
    private String responseFlag;

    public String getFormGroup() {
        return formGroup;
    }

    public void setFormGroup(String formGroup) {
        this.formGroup = formGroup;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResponseFlag() {
        return responseFlag;
    }

    public void setResponseFlag(String responseFlag) {
        this.responseFlag = responseFlag;
    }
}
