package top.yzlin.sharedbanlist;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import top.yzlin.tools.Tools;

import java.util.*;

public class BanListAPI {
    //TODO:胡某昨天欠我3000金
    private static final String BAN_API = "http://api.skygrass.club/Robot/Api";

    private static final String FUNC_GET_TOKEN = "GetTokenByGroupAndOperator";
    private static final String FUNC_IS_IN_BANLIST = "IsInBanList";
    private static final String FUNC_ADD_QQ_TO_BANLIST = "AddQqToBanList";
    private static final String FUNC_DELETE_QQ_FROM_GROUP = "DeleteQqFromGroup";

    private static final String PARAM_T = "t";
    private static final String PARAM_PRIVATE_KEY = "private_key";
    private static final String PARAM_OPERATOR_QQ = "operator_qq";
    private static final String PARAM_FROM_GROUP = "from_group";
    private static final String PARAM_SIGN = "sign";
    private static final String PARAM_TOKEN = "token";
    private static final String PARAM_TARGET_QQ = "target_qq";
    private static final String PARAM_DESCRIBE = "describe";

    private static final String PARAM_TS = "ts";

    private String fromGroup;
    private String privateKey;
    private String operatorQQ;

    private String token;
    private long addTime = 0;

    private HashMap<String, String> tokenMap = new HashMap<>();
    private HashMap<String, String> isBanMap = new HashMap<>();
    private HashMap<String, String> addQQMap = new HashMap<>();
    private HashMap<String, String> deleteMap = new HashMap<>();


    public BanListAPI(String fromGroup, String privateKey, String operatorQQ) {
        this.fromGroup = fromGroup;
        this.privateKey = privateKey;
        this.operatorQQ = operatorQQ;

        tokenMap.put(PARAM_T, FUNC_GET_TOKEN);
        tokenMap.put(PARAM_PRIVATE_KEY, privateKey);
        tokenMap.put(PARAM_OPERATOR_QQ, operatorQQ);
        tokenMap.put(PARAM_FROM_GROUP, fromGroup);

        isBanMap.put(PARAM_T, FUNC_IS_IN_BANLIST);
        isBanMap.put(PARAM_FROM_GROUP, fromGroup);

        addQQMap.put(PARAM_T, FUNC_ADD_QQ_TO_BANLIST);
        addQQMap.put(PARAM_FROM_GROUP, fromGroup);

//        deleteMap.put()
    }

    public static void main(String[] args) {
        BanListAPI banListAPI = new BanListAPI("531501258", "zztsdcssmcn", "499680328");

//        System.out.println(banListAPI.addQQToBanList("499680333","test"));
        System.out.println(JSON.toJSON(banListAPI.isInBanList("499680333")));

    }

    private static String makeParam(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        map.forEach((k, v) -> sb.append(k).append('=').append(v).append('&'));
        long time = System.currentTimeMillis();
        sb.append(PARAM_TS).append('=').append(time).append('&');
        Collection<String> collection = map.values();
        String[] data = new String[collection.size() + 1];
        data = collection.toArray(data);
        data[data.length - 1] = Long.toString(time);
        sb.append(PARAM_SIGN).append('=').append(appendSgin(data));
        return sb.toString();
    }

    private static String appendSgin(String[] params) {
        Arrays.sort(params);
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : params) {
            stringBuilder.append(s);
        }
        stringBuilder.append("@SkyGrassClub");
        return Tools.MD5(stringBuilder.toString());
    }

    public List<BanInfo> isInBanList(String targetQQ) {
        isBanMap.put(PARAM_TARGET_QQ, targetQQ);
        isBanMap.put(PARAM_TOKEN, getToken());
        JSONObject jo = JSON.parseObject(Tools.sendPost(BAN_API, makeParam(isBanMap)));
        if (jo.getIntValue("errcode") == 0) {
            jo = jo.getJSONObject("data");
            return jo.getJSONArray("banList").toJavaList(BanInfo.class);
        } else {
            Tools.print("BanList获取token失败:" + jo.getIntValue("errcode") + ':' + jo.getString("message"));
            return Collections.emptyList();
        }
    }

    public boolean addQQToBanList(String targetQQ, String describe) {
        addQQMap.put(PARAM_TARGET_QQ, targetQQ);
        addQQMap.put(PARAM_DESCRIBE, describe);
        addQQMap.put(PARAM_TOKEN, getToken());
        JSONObject jo = JSON.parseObject(Tools.sendPost(BAN_API, makeParam(addQQMap)));
        if (jo.getIntValue("errcode") == 0) {
            return true;
        } else {
            Tools.print("添加QQ失败:" + jo.getIntValue("errcode") + ':' + jo.getString("message"));
            return false;
        }
    }

    public boolean deleteQqFromGroup() {
        return false;
    }

    public String getToken() {
        return System.currentTimeMillis() - addTime > 1000 * 60 * 60 * 23 * 7 ?
                token = getNewToken() : token;
    }

    private String getNewToken() {
        JSONObject jo = JSONObject.parseObject(Tools.sendPost(BAN_API, makeParam(tokenMap)));
        if (jo.getIntValue("errcode") == 0) {
            jo = jo.getJSONObject("data");
            addTime = jo.getLongValue("add_time");
            return jo.getString("token");
        } else {
            Tools.print("BanList获取token失败:" + jo.getIntValue("errcode") + ':' + jo.getString("message"));
            return "";
        }
    }
}
