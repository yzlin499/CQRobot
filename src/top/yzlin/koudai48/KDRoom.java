package top.yzlin.koudai48;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import top.yzlin.monitoring.BaseData;
import top.yzlin.tools.SetConnection;
import top.yzlin.tools.Tools;

import java.io.File;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

public class KDRoom implements BaseData<KDRoomInfo> {
    /*创建文档*/
    static {
        File tf = new File("doc\\KDRoomConfiguration");
        if (!tf.exists()) {
            tf.mkdirs();
        }
    }

    private String roomID;
    private KDValidation kdValidation;
    private Set<KDRoomType> msgTypeSet = EnumSet.allOf(KDRoomType.class);
    private String token;
    private SetConnection conn = connection -> {
        connection.setRequestProperty("token", token);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("version", "5.0.1");
        connection.setRequestProperty("build", "52127");
        connection.setRequestProperty("os", "android");
    };

    /**
     * 实例化一个对象
     *
     * @param account    口袋48账号
     * @param memberName 成员名字
     */
    public KDRoom(String account, String memberName) {
        this(new KDValidation(account),memberName);
    }

    public KDRoom(KDValidation kdValidation, String memberName) {
        if ((roomID = getRoomID(memberName)) == null) {
            Tools.print("房间获取失败，程序直接结束");
            return;
        }
        this.kdValidation=kdValidation;
        token = kdValidation.getToken();
    }

    public void setFunction(KDRoomType kdRoomType,boolean status){
        if(status){
            setFunctionOn(kdRoomType);
        }else{
            setFunctionDown(kdRoomType);
        }
    }

    public void setFunctionOn(KDRoomType kdRoomType) {
        msgTypeSet.add(kdRoomType);
    }

    public void setFunctionDown(KDRoomType kdRoomType) {
        msgTypeSet.remove(kdRoomType);
    }

    public void setTextOpen(boolean status){
        setFunction(KDRoomType.TEXT,status);
    }

    public void setLiveOpen(boolean status){
        setFunction(KDRoomType.LIVE,status);
    }

    public void setImageOpen(boolean status){
        setFunction(KDRoomType.IMAGE,status);
    }

    public void setIdolFlipOpen(boolean status){
        setFunction(KDRoomType.IDOLFLIP,status);
    }

    public void setFaiPaiOpen(boolean status){
        setFunction(KDRoomType.FAIPAI_TEXT,status);
    }

    public void setAudioOpen(boolean status){
        setFunction(KDRoomType.AUDIO,status);
    }

    public void setDianTaiOpen(boolean status){
        setFunction(KDRoomType.DIANTAI,status);
    }

    /**
     * 返回最新的消息,如果获取失败返回null
     *
     * @param time 上一次收集的时间
     * @return RoomInfo数组
     */
    public KDRoomInfo[] getData(long time) {
        return getData(data -> data.getMsgTime() > time);
    }

    @Override
    public KDRoomInfo[] getData(Predicate<KDRoomInfo> predicate) {
        JSONObject result = JSONObject.parseObject(Tools.sendPost(
                "https://pjuju.48.cn/imsystem/api/im/v1/member/room/message/mainpage",
                "{\"roomId\":\"" + roomID + "\",\"chatType\":\"0\",\"lastTime\":\"0\",\"limit\":\"10\"}",
                conn));
        if (result.getIntValue("status") == 401) {
            Tools.print(result.getString("message"));
            token = kdValidation.getNewToken();
            return getData(predicate);
        } else if (result.getIntValue("status") != 200) {
            Tools.print(result.getString("message"));
            return new KDRoomInfo[0];
        }
        return result.getJSONObject("content").getJSONArray("data").toJavaList(JSONObject.class)
                .stream()
                .map(data -> {
                    try {
                        JSONObject extInfo = JSONObject.parseObject(data.getString("extInfo"));
                        KDRoomInfo temp = new KDRoomInfo();
                        KDRoomType type = KDRoomType.parse(extInfo.getString("messageObject"));
                        temp.setMsgType(type);
                        if (type == null) {
                            if (!"juju".equals(extInfo.getString("source"))) {
                                Tools.print("未知数据:" + extInfo);
                            }
                            return null;
                        } else if (!msgTypeSet.contains(type)) {
                            return null;
                        }
                        temp.setMsgTime(data.getLong("msgTime"));
                        temp.setSender(extInfo.getString("senderName"));
                        switch (type) {
                            case TEXT:
                                temp.setMsg(extInfo.getString("text"));
                                break;
                            case FAIPAI_TEXT:
                                temp.setText(extInfo.getString("faipaiContent"));
                                temp.setMsg(extInfo.getString("messageText"));
                                break;
                            case LIVE:
                            case DIANTAI:
                                temp.setMsg("https://h5.48.cn/2017appshare/memberLiveShare/index.html?id=" +
                                        extInfo.getString("referenceObjectId"));
                                temp.setText(extInfo.getString("referenceContent"));
                                break;
                            case AUDIO:
                            case VIDEO_RECORD:
                            case IMAGE:
                                try {
                                    temp.setMsg(JSONObject.parseObject(
                                            data.getString("bodys"))
                                            .getString("url"));
                                } catch (JSONException e) {
                                    temp.setMsg(JSONObject.parseObject(
                                            data.getString("bodys").substring(1))
                                            .getString("url"));
                                }
                                break;
                            case IDOLFLIP:
                                temp.setMsg(extInfo.getString("idolFlipContent"));
                                temp.setText(extInfo.getString("idolFlipTitle"));
                                break;
                            default:
                                temp.setMsg("未知信息类型并且此处代码不可达");
                        }
                        return temp;
                    } catch (JSONException e) {
                        Tools.print(data);
                        return null;
                    }
                }).filter(Objects::nonNull)
                .filter(predicate)
                .toArray(KDRoomInfo[]::new);
    }

    /**
     * 获取成员的房间ID
     *
     * @param memberName 成员姓名
     * @return 返回成员ID
     */
    private String getRoomID(String memberName) {
        JSONObject result = JSONObject.parseObject(Tools.sendPost(
                "https://pjuju.48.cn/imsystem/api/im/v1/search",
                "{\"roomName\":\"" + memberName + "\"}",
                conn
        ));
        if (!"200".equals(result.getString("status"))) {
            Tools.print("获取房间ID失败，炸了，散了吧");
            return null;
        }
        JSONArray aData = result.getJSONObject("content").getJSONArray("data");
        if (aData.size() <= 0) {
            Tools.print("搜不到这个人啊");
            return null;
        }
        for (int i = aData.size() - 1; i >= 0; i--) {
            result = aData.getJSONObject(i);
            if (0 == result.getIntValue("roomType")) {
                Tools.print("成员房间名：" + result.getString("roomName") + ":[" + result.getString("roomId") + ']');
                return result.getString("roomId");
            }
        }
        Tools.print("查到消息但是查不到成员");
        return null;
    }
}
