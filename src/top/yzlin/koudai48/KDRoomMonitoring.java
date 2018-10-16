package top.yzlin.koudai48;

import top.yzlin.cqrobotsdk.CQRobot;
import top.yzlin.monitoring.Monitoring;
import top.yzlin.tools.Tools;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Clock;

public class KDRoomMonitoring extends Monitoring<KDRoomInfo> {
    private String groupID;
    private CQRobot cqRobot;
    private static String CQPath = null;

    public KDRoomMonitoring(KDValidation kdValidation, String memberName,String groupID,CQRobot cqRobot){
        this(new KDRoom(kdValidation,memberName),groupID,cqRobot);
    }

    public KDRoomMonitoring(KDRoom kdRoom, String groupID,CQRobot cqRobot) {
        super(kdRoom);
        this.groupID = groupID;
        this.cqRobot = cqRobot;
        setConsumer(k->{
            for (KDRoomInfo kdRoomInfo : k) {
                sendMsg(kdRoomInfo);
            }
        });
    }

    @Override
    protected boolean predicate(KDRoomInfo newData, KDRoomInfo oldData) {
        return newData.getMsgTime()>oldData.getMsgTime();
    }

    /**
     * 保存图片并且返回图片名称,免费版的酷Q是无法发送图片的,慎用这个函数,air版本不要调用这个函数
     *
     * @param file 图片的地址
     * @return 如果设置了酷Q的路径, 那么就会保存图片并且返回相应CQ码, 否则返回参数本身的短地址
     */
    private String saveAudio(String file) {
        if (CQPath == null) {
            return Tools.getTinyURL(file);
        } else {
            String date = "" + Clock.systemDefaultZone().millis() + ".amr";
            try (FileOutputStream fps = new FileOutputStream(CQPath + "\\data\\record\\" + date)) {
                InputStream fis = new URL(file).openStream();
                int data;
                while ((data = fis.read()) != -1) {
                    fps.write(data);
                }
                fis.close();
                fps.close();
                return CQRobot.getAudioCQCode(date, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 保存语音并且返回语音名称,免费版的酷Q是无法发送语音的,慎用这个函数,air版本不要调用这个函数
     *
     * @param file 语音的地址
     * @return 如果设置了酷Q的路径, 那么就会保存图片并且返回相应CQ码, 否则返回参数本身的短地址
     */
    private String saveImage(String file) {
        if (CQPath == null) {
            return Tools.getTinyURL(file);
        } else {
            String date = "" + Clock.systemDefaultZone().millis() + ".jpg";
            try {
                ImageIO.write(ImageIO.read(new URL(file)), "JPG", new File(CQPath + "\\data\\image\\" + date));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return CQRobot.getImgCQCode(date);
        }
    }

    /**
     * 自定义发送方法
     *
     * @param kdRoomInfo
     * @return
     */
    protected String sendText(KDRoomInfo kdRoomInfo) {
        switch (kdRoomInfo.getMsgType()) {
            case TEXT:
                return kdRoomInfo.getSender() + "说:" + kdRoomInfo.getMsg();
            case IMAGE:
                return kdRoomInfo.getSender() + "发了一张图片:" + kdRoomInfo.getMsg();
            case AUDIO:
                return kdRoomInfo.getSender() + "发了一段音频" + kdRoomInfo.getMsg();
            case FAIPAI_TEXT:
                return kdRoomInfo.getSender() + ':' + kdRoomInfo.getMsg() + "\n被翻牌:" + kdRoomInfo.getText();
            case IDOLFLIP:
                return kdRoomInfo.getSender() + "翻牌了问题:" + kdRoomInfo.getMsg() + "进入口袋48查看回答";
            case LIVE:
                return kdRoomInfo.getSender() + "开直播啦";
            case DIANTAI:
                return kdRoomInfo.getSender() + "开电台啦";
            default:
                return "来自口袋的:" + kdRoomInfo.getMsg();
        }
    }

    /**
     * 设置各类文件的保存路径,如果这个函数没有调用,那么将比较粗糙的处理图片信息<br>
     * 如果你的酷Q是AIR版本(免费版本),那么请不要使用这个函数,谢谢,免得信息错误
     *
     * @param cqPath 酷Q的路径
     * @return 设置成功将返回true
     */
    public boolean setCQPath(String cqPath) {
        File f = new File(cqPath);
        if (f.exists()) {
            CQPath = cqPath;
            return true;
        }
        return false;
    }

    /**
     * 重写sendText方法时，使用这个方法来向酷Q发送信息
     */
    private void sendMsg(KDRoomInfo data) {
        switch (data.getMsgType()) {
            case IMAGE:
                data.setMsg(saveImage(data.getMsg()));
                break;
            case AUDIO:
                data.setMsg(saveAudio(data.getMsg()));
                break;
            case LIVE:
            case DIANTAI:
                data.setMsg(Tools.getTinyURL(data.getMsg()));
                break;
            default:
        }
        Tools.print(data.getSender() + "发送" + data.getMsgType().getCnName() + data.getMsg());
        cqRobot.sendGroupMsg(groupID, sendText(data));
    }

}
