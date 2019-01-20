package top.yzlin.sharedbanlist;

import com.alibaba.fastjson.JSONObject;
import top.yzlin.tools.Tools;

import java.util.*;

public class BanListAPI {
    private static final String BAN_API = "http://api.skygrass.club/Robot/Api";

    private static final String FUNC_GET_TOKEN = "GetTokenByGroupAndOperator";
    private static final String FUNC_IS_IN_BAN_LIST = "IsInBanList";
    private static final String FUNC_ADD_QQ_TO_BAN_LIST = "AddQqToBanList";
    private static final String FUNC_DELETE_QQ_FROM_GROUP = "DeleteQqFromGroup";
    private static final String FUNC_GET_BAN_LIST = "GetBanListFromGroup";

    private static final String PARAM_T = "t";
    private static final String PARAM_PRIVATE_KEY = "private_key";
    private static final String PARAM_OPERATOR_QQ = "operator_qq";
    private static final String PARAM_FROM_GROUP = "from_group";
    private static final String PARAM_SIGN = "sign";
    private static final String PARAM_TOKEN = "token";
    private static final String PARAM_TARGET_QQ = "target_qq";
    private static final String PARAM_DESCRIBE = "describe";
    private static final String PARAM_TS = "ts";


    private String token;
    private long addTime = 0;
    private String fromGroup;

    private HashMap<String, String> tokenMap = new HashMap<>();
    private HashMap<String, String> isBanMap = new HashMap<>();
    private HashMap<String, String> addQQMap = new HashMap<>();
    private HashMap<String, String> deleteMap = new HashMap<>();
    private HashMap<String, String> listMap = new HashMap<>();


    public BanListAPI(String fromGroup, String privateKey, String operatorQQ) {
        tokenMap.put(PARAM_T, FUNC_GET_TOKEN);
        tokenMap.put(PARAM_PRIVATE_KEY, privateKey);
        tokenMap.put(PARAM_OPERATOR_QQ, operatorQQ);
        tokenMap.put(PARAM_FROM_GROUP, fromGroup);

        isBanMap.put(PARAM_T, FUNC_IS_IN_BAN_LIST);
        isBanMap.put(PARAM_FROM_GROUP, fromGroup);

        addQQMap.put(PARAM_T, FUNC_ADD_QQ_TO_BAN_LIST);
        addQQMap.put(PARAM_FROM_GROUP, fromGroup);

        deleteMap.put(PARAM_T, FUNC_DELETE_QQ_FROM_GROUP);
        deleteMap.put(PARAM_FROM_GROUP, fromGroup);

        listMap.put(PARAM_T, FUNC_GET_BAN_LIST);
        listMap.put(PARAM_FROM_GROUP, fromGroup);

        this.fromGroup = fromGroup;
    }

    public String getFromGroup() {
        return fromGroup;
    }

    public List<BanInfo> isInBanList(String targetQQ) {
        isBanMap.put(PARAM_TARGET_QQ, targetQQ);
        return Optional.ofNullable(sendData(isBanMap))
                .map(e -> e.getJSONObject("data")
                        .getJSONArray("banList")
                        .toJavaList(BanInfo.class))
                .orElse(Collections.emptyList());
    }

    public boolean addQQToBanList(String targetQQ, String describe) {
        addQQMap.put(PARAM_TARGET_QQ, targetQQ);
        addQQMap.put(PARAM_DESCRIBE, describe);
        return Optional.ofNullable(sendData(addQQMap)).isPresent();
    }

    public boolean deleteQqFromGroup(String targetQQ) {
        deleteMap.put(PARAM_TARGET_QQ, targetQQ);
        return Optional.ofNullable(sendData(deleteMap)).isPresent();
    }

    public List<BanInfo> getBanList() {
        return Optional.ofNullable(sendData(listMap))
                .map(e -> e.getJSONObject("data")
                        .getJSONArray("banList")
                        .toJavaList(BanInfo.class))
                .orElse(Collections.emptyList());
    }

    private String getToken() {
        return System.currentTimeMillis() - addTime > 1000 * 60 * 60 * 23 * 7 ?
                token = getNewToken() : token;
    }

    private String getNewToken() {
        JSONObject jo = JSONObject.parseObject(Tools.sendPost(BAN_API, makeParam(tokenMap)));
        if (jo.getIntValue("errcode") == BanListErrcode.SUCCESS) {
            jo = jo.getJSONObject("data");
            addTime = jo.getLongValue("add_time");
            return jo.getString("token");
        } else {
            Tools.print("BanList获取token失败:" + jo.getIntValue("errcode") + ':' + jo.getString("message"));
            return "";
        }
    }

    private JSONObject sendData(Map<String, String> map) {
        map.put(PARAM_TOKEN, getToken());
        JSONObject jo = JSONObject.parseObject(Tools.sendPost(BAN_API, makeParam(map)));
        switch (jo.getIntValue("errcode")) {
            case BanListErrcode.SUCCESS:
                return jo;
            case BanListErrcode.TOKEN_OVERDUE:
                token = getNewToken();
                return sendData(map);
            default:
                Tools.print(map.get(PARAM_T) + "失败:" + jo.getIntValue("errcode") + ':' + jo.getString("message"));
                return null;
        }
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
}
