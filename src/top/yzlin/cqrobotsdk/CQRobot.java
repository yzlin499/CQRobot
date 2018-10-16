package top.yzlin.cqrobotsdk;

import top.yzlin.cqrobotsdk.msginterface.EventSolution;

public interface CQRobot {

    void sendPersonMsg(String qqID, String msg);

    void sendGroupMsg(String groupID, String msg);

    void sendDiscussMsg(String discussID, String msg);

    boolean addMsgSolution(EventSolution msm);

    boolean removeMsgSolution(EventSolution msm);

    void close();

    /**
     * 获取图片的CQ码<br>
     * 图片存放在酷Q目录的data\image\下<br><br>
     * 举例：<b>getImgCQCode("1.jpg");</b>（发送data\image\1.jpg）<br><br>
     * 发送图片信息的时候的方法为:<b>sendxxxxxMsg(getImgCQCode("xxx"));</b>
     * 会员才能发送图片
     *
     * @param imgFile 图片的名称
     * @return 图片的CQ码
     */
    static String getImgCQCode(String imgFile) {
        return "[CQ:image,file=" + imgFile + "]";
    }

    /**
     * 获取音频的CQ码<br>
     * 图片存放在酷Q目录的data\record\下<br><br>
     * 举例：<b>getImgCQCode("1");</b>（发送data\image\1.jpg）<br><br>
     * 发送图片信息的时候的方法为:<b>sendxxxxxMsg(getImgCQCode("xxx"));</b>
     * 会员才能发送音频
     * @param audioFile 音频的名称
     * @return 音频的CQ码
     */
    static String getAudioCQCode(String audioFile, boolean magic) {
        return "[CQ:record,file=" + audioFile + (magic ? ",magic=true" : "") + "]";
    }
}
