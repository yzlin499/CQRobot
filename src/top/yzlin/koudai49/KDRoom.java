package top.yzlin.koudai49;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import top.yzlin.monitoring.BaseData;
import top.yzlin.tools.SetConnection;
import top.yzlin.tools.Tools;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

public class KDRoom implements BaseData<KDRoomInfo> {
    private String requestParm;
    private MemberInfo memberInfo;
    private KDValidation kdValidation;
    private Set<KDRoomType> msgTypeSet = EnumSet.allOf(KDRoomType.class);
    private SetConnection conn;

    /**
     * 实例化一个对象
     *
     * @param account    口袋48账号
     * @param member 成员名字
     */
    public KDRoom(String account, String member) {
        this(new KDValidation(account), member);
    }

    public KDRoom(KDValidation kdValidation, String member) {
        this.kdValidation=kdValidation;
        if (member.contains("-")) {
            memberInfo = KD49Data.getInstance().getMemberInfoByNickName(member);
        } else {
            memberInfo = KD49Data.getInstance().getMemberInfoByName(member);
            if (memberInfo == null) {
                try {
                    memberInfo = KD49Data.getInstance().getMemberInfoById(Integer.parseInt(member));
                    if (memberInfo == null) {
                        memberInfo = KD49Data.getInstance().getMemberInfoByRoomId(Integer.parseInt(member));
                    }
                } catch (NumberFormatException ignored) {
                    Tools.print("无法初始化房间数据");
                    return;
                }
            }
        }
        conn = KD49API.getTokenApiHeader(kdValidation.getToken());
        requestParm = new JSONObject()
                .fluentPut("needTop1Msg", false)
                .fluentPut("nextTime", System.currentTimeMillis())
                .fluentPut("roomId", memberInfo.getRoomId())
                .fluentPut("ownerId", memberInfo.getId())
                .toString();
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
        setFunction(KDRoomType.LIVE_PUSH, status);
    }

    public void setImageOpen(boolean status){
        setFunction(KDRoomType.IMAGE,status);
    }

    public void setIdolFlipOpen(boolean status){
        setFunction(KDRoomType.FLIP_CARD, status);
    }

    public void setReplyOpen(boolean status) {
        setFunction(KDRoomType.REPLY, status);
    }

    public void setAudioOpen(boolean status){
        setFunction(KDRoomType.AUDIO,status);
    }

    /**
     * 返回最新的消息,如果获取失败返回null
     *
     * @param time 上一次收集的时间
     * @return RoomInfo数组
     */
    public KDRoomInfo[] getData(long time) {
        return makeData(j -> j.getLongValue("msgTime") > time, data -> true);
    }

    @Override
    public KDRoomInfo[] getData(Predicate<KDRoomInfo> predicate) {
        return makeData(j -> true, predicate);
    }

    public KDRoomInfo[] makeData(Predicate<JSONObject> before, Predicate<KDRoomInfo> predicate) {
        JSONObject result = JSONObject.parseObject(Tools.sendPost(KD49API.KD_ROOM, requestParm, conn));
        if (result.getIntValue("status") > 400000) {
            Tools.print(result.getString("message"));
            conn = KD49API.getTokenApiHeader(kdValidation.getNewToken());
            return makeData(before, predicate);
        } else if (result.getIntValue("status") != 200) {
            Tools.print(result.getString("message"));
            return new KDRoomInfo[0];
        }
        return result.getJSONObject("content").getJSONArray("message").toJavaList(JSONObject.class)
                .stream()
                .filter(before)
                .map(data -> {
                    try {
                        KDRoomInfo temp = new KDRoomInfo();
                        JSONObject extInfo = JSONObject.parseObject(data.getString("extInfo"));
                        KDRoomType type = KDRoomType.parse(extInfo.getString("messageType"));
                        temp.setMsgType(type);
                        if (type == null) {
                            Tools.print("未知数据类型，类型无法解析:" + extInfo);
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
                            case REPLY:
                                temp.setText(extInfo.getString("faipaiContent"));
                                temp.setMsg(extInfo.getString("messageText"));
                                break;
                            case LIVE_PUSH:
                                temp.setMsg("https://h5.48.cn/2017appshare/memberLiveShare/index.html?id=" +
                                        extInfo.getString("referenceObjectId"));
                                temp.setText(extInfo.getString("referenceContent"));
                                break;
                            case AUDIO:
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
                            case FLIP_CARD:
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


}
