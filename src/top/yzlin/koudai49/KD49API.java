package top.yzlin.koudai49;

import com.alibaba.fastjson.JSONObject;
import top.yzlin.tools.SetConnection;

public final class KD49API {
    public static final String VALIDATION_API = "https://pocketapi.48.cn/user/api/v1/login/app/mobile";
    public static final String KD_ROOM = "https://pocketapi.48.cn/im/api/v1/chatroom/msg/list/homeowner";

    private static final String APPINFO = new JSONObject()
            .fluentPut("vendor", "apple")
            .fluentPut("deviceId", "DD6AA6A9-E3FE-445B-9C5B-DBB70917A123")
            .fluentPut("appVersion", "6.0.0")
            .fluentPut("appBuild", "190409")
            .fluentPut("osVersion", "12.2.0")
            .fluentPut("osType", "ios")
            .fluentPut("deviceName", "iphone")
            .fluentPut("os", "ios")
            .toString();
    public static final SetConnection API_HEADER = conn -> {
        conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        conn.setRequestProperty("User-Agent", "PocketFans201807/6.0.0 (iPhone; iOS 12.2; Scale/2.00)");
        conn.setRequestProperty("appInfo", APPINFO);
    };

    public static SetConnection getTokenApiHeader(String token) {
        return conn -> {
            conn.setRequestProperty("token", token);
            API_HEADER.setConnection(conn);
        };
    }

}
