package top.yzlin.cqrobotsdk.cqinfo;

/**
 * 信息型消息
 */
public class MsgInfo extends AbstractInfo{
    private String msg = "";
    private String font = "";
    private String nick="";
    private String sex="";
    private String age="";
    /**
     * 一个来源于httpapi的字段
     */
    private String rawMsg = "";

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getRawMsg() {
        return rawMsg;
    }

    public void setRawMsg(String rawMsg) {
        this.rawMsg = rawMsg;
    }
}
